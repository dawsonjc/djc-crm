package com.auth_service.http

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import java.util.Base64
import java.util.concurrent.CompletionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

enum class RequestMethod { GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS }

sealed interface HttpAuthentication {
    class Basic(val username: String, val password: String) : HttpAuthentication
    class Bearer(val token: String) : HttpAuthentication
}

/** Server-side HTTP requests. HTTP errors are returned; transport failures are thrown. */
class HttpRequests(
    private val userAgent: String = "DJC-CRM-Auth-Service",
    private val client: HttpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .followRedirects(HttpClient.Redirect.NEVER)
        .build(),
) {
    suspend fun request(
        url: String,
        method: RequestMethod = RequestMethod.GET,
        body: String? = null,
        contentType: String = "application/json; charset=UTF-8",
        headers: Map<String, String> = emptyMap(),
        authentication: HttpAuthentication? = null,
        timeout: Duration = Duration.ofSeconds(15),
    ): HttpResponse<String> = send(
        url, method, body?.toByteArray(Charsets.UTF_8), contentType, headers,
        authentication, timeout, HttpResponse.BodyHandlers.ofString(),
    )

    suspend fun readUrlAsString(
        url: String,
        method: RequestMethod = RequestMethod.GET,
        body: String? = null,
        contentType: String = "application/json; charset=UTF-8",
        headers: Map<String, String> = emptyMap(),
        authentication: HttpAuthentication? = null,
        timeout: Duration = Duration.ofSeconds(15),
    ): String = request(url, method, body, contentType, headers, authentication, timeout).body()

    /** Preserves response bytes and repeated headers for proxy endpoints. */
    suspend fun requestBytes(
        url: String,
        method: RequestMethod = RequestMethod.GET,
        body: ByteArray? = null,
        contentType: String = "application/json",
        headers: Map<String, String> = emptyMap(),
        authentication: HttpAuthentication? = null,
        timeout: Duration = Duration.ofSeconds(15),
    ): HttpResponse<ByteArray> = send(
        url, method, body, contentType, headers, authentication, timeout,
        HttpResponse.BodyHandlers.ofByteArray(),
    )

    private suspend fun <T> send(
        url: String,
        method: RequestMethod,
        body: ByteArray?,
        contentType: String,
        headers: Map<String, String>,
        authentication: HttpAuthentication?,
        timeout: Duration,
        handler: HttpResponse.BodyHandler<T>,
    ): HttpResponse<T> {
        val uri = URI.create(url)
        require(uri.scheme in setOf("http", "https") && uri.host != null) {
            "Expected an absolute HTTP or HTTPS URL."
        }
        require(uri.userInfo == null) { "Use the authentication option instead of URL credentials." }
        require(authentication == null || headers.keys.none { it.equals("Authorization", true) }) {
            "Specify authentication or an Authorization header, not both."
        }
        val builder = HttpRequest.newBuilder(uri)
            .timeout(timeout)
            .header("User-Agent", userAgent)
            .header("Cache-Control", "no-cache")
            .method(method.name, body?.let(HttpRequest.BodyPublishers::ofByteArray)
                ?: HttpRequest.BodyPublishers.noBody())
        if (body != null) builder.header("Content-Type", contentType)
        headers.forEach { (name, value) -> builder.setHeader(name, value) }
        when (authentication) {
            is HttpAuthentication.Basic -> {
                require(':' !in authentication.username) { "Basic authentication usernames cannot contain a colon." }
                val credentials = "${authentication.username}:${authentication.password}"
                builder.setHeader("Authorization", "Basic " +
                    Base64.getEncoder().encodeToString(credentials.toByteArray(Charsets.UTF_8)))
            }
            is HttpAuthentication.Bearer -> builder.setHeader("Authorization", "Bearer ${authentication.token}")
            null -> Unit
        }
        val request = builder.build()
        return suspendCoroutine { continuation ->
            client.sendAsync(request, handler).whenComplete { response, error ->
                if (error != null) {
                    continuation.resumeWithException(
                        if (error is CompletionException) error.cause ?: error else error,
                    )
                } else continuation.resume(response)
            }
        }
    }
}
