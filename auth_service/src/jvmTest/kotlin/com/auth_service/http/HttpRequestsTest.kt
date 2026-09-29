package com.auth_service.http

import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.InetSocketAddress
import java.net.http.HttpTimeoutException
import java.time.Duration
import java.util.Base64
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

class HttpRequestsTest {
    private val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0).apply { start() }
    private val url get() = "http://127.0.0.1:${server.address.port}/test"
    private val client = HttpRequests()

    @AfterEach
    fun stopServer() = server.stop(0)

    @Test
    fun `GET preserves multiline text and respects response charset`() {
        server.createContext("/test") {
            it.responseHeaders.set("Content-Type", "text/plain; charset=ISO-8859-1")
            it.reply(200, "café\nsecond line".toByteArray(Charsets.ISO_8859_1))
        }

        assertEquals("café\nsecond line", await { client.readUrlAsString(url) })
    }

    @Test
    fun `methods body basic auth and custom headers reach the server`() {
        val received = CompletableFuture<List<String>>()
        server.createContext("/test") {
            received.complete(listOf(
                it.requestMethod,
                it.requestBody.readBytes().toString(Charsets.UTF_8),
                it.requestHeaders.getFirst("Authorization"),
                it.requestHeaders.getFirst("Content-Type"),
                it.requestHeaders.getFirst("X-Test"),
            ))
            it.reply(200, "ok".toByteArray())
        }
        await {
            client.request(url, RequestMethod.PUT, "héllo", "text/plain; charset=UTF-8",
                mapOf("X-Test" to "value"), HttpAuthentication.Basic("user", "pass"))
        }
        assertEquals(listOf("PUT", "héllo", "Basic " +
            Base64.getEncoder().encodeToString("user:pass".toByteArray()),
            "text/plain; charset=UTF-8", "value"), received.get(5, TimeUnit.SECONDS))
    }

    @Test
    fun `HTTP errors retain body status cookies and bearer auth`() {
        val authorization = CompletableFuture<String>()
        server.createContext("/test") {
            authorization.complete(it.requestHeaders.getFirst("Authorization"))
            it.responseHeaders.add("Set-Cookie", "first=1")
            it.responseHeaders.add("Set-Cookie", "second=2")
            it.reply(401, "denied".toByteArray())
        }
        val response = await {
            client.requestBytes(url, RequestMethod.POST, "{}".toByteArray(),
                authentication = HttpAuthentication.Bearer("test-token"))
        }
        assertEquals(401, response.statusCode())
        assertEquals("denied", response.body().toString(Charsets.UTF_8))
        assertEquals(2, response.headers().allValues("Set-Cookie").size)
        assertEquals("Bearer test-token", authorization.get(5, TimeUnit.SECONDS))
    }

    @Test
    fun `redirects are not followed`() {
        server.createContext("/test") {
            it.responseHeaders.set("Location", "/target")
            it.reply(302, "redirect".toByteArray())
        }
        assertEquals(302, await { client.request(url) }.statusCode())
    }

    @Test
    fun `invalid URLs and ambiguous authentication fail explicitly`() {
        assertThrows(IllegalArgumentException::class.java) {
            await { client.request("localhost:8080") }
        }
        assertThrows(IllegalArgumentException::class.java) {
            await { client.request(url, headers = mapOf("authorization" to "other"),
                authentication = HttpAuthentication.Bearer("token")) }
        }
    }

    @Test
    fun `timeouts propagate rather than returning empty text`() {
        server.createContext("/test") {
            Thread.sleep(300)
            it.close()
        }
        assertThrows(HttpTimeoutException::class.java) {
            await { client.request(url, timeout = Duration.ofMillis(50)) }
        }
    }

    private fun HttpExchange.reply(status: Int, body: ByteArray) {
        sendResponseHeaders(status, body.size.toLong())
        responseBody.use { it.write(body) }
        close()
    }

    private fun <T> await(block: suspend () -> T): T {
        val result = CompletableFuture<Result<T>>()
        block.startCoroutine(object : Continuation<T> {
            override val context = EmptyCoroutineContext
            override fun resumeWith(value: Result<T>) { result.complete(value) }
        })
        return result.get(5, TimeUnit.SECONDS).getOrThrow()
    }
}
