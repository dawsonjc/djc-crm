package com.auth_service.store

import com.auth_service.http.HttpRequests
import com.auth_service.model.Company
import com.sun.net.httpserver.HttpServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.InetSocketAddress
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

class CompanyStoreSessionTest {
    private val server = HttpServer.create(InetSocketAddress("127.0.0.1", 0), 0).apply { start() }
    private val store = CompanyStore("http://127.0.0.1:${server.address.port}", "test-secret", HttpRequests())
    @AfterEach
    fun stopServer() = server.stop(0)
    @Test
    fun `requests forward each callers session separately`() {
        val received = mutableListOf<String>()
        server.createContext("/auth/company/add") { exchange ->
            received.add(exchange.requestHeaders.getFirst("Cookie"))
            val response = """{"success":true,"data":{"name":"Saved company"}}""".toByteArray()
            exchange.requestBody.close()
            exchange.sendResponseHeaders(200, response.size.toLong())
            exchange.responseBody.use { it.write(response) }
            exchange.close()
        }
        assertEquals("Saved company", await { store.add(Company(name = "First"), "session-one") }.name)
        await { store.add(Company(name = "Second"), "session-two") }
        assertEquals(listOf("JSESSIONID=session-one", "JSESSIONID=session-two"), received)
    }
    @Test
    fun `expired backend sessions are authentication failures`() {
        server.createContext("/auth/company/add") { exchange ->
            exchange.requestBody.close()
            exchange.sendResponseHeaders(401, -1)
            exchange.close()
        }
        assertThrows(BackendSessionExpiredException::class.java) {
            await { store.add(Company(name = "Brewery"), "expired-session") }
        }
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