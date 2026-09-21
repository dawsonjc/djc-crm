package com.auth_service.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf
import com.varabyte.kobweb.api.http.bytes
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

private val loginClient = HttpClient.newBuilder()
    .connectTimeout(Duration.ofSeconds(10))
    .followRedirects(HttpClient.Redirect.NEVER)
    .build()

// Kobweb prefixes API routes with /api, so this handler is POST /api/login.
@Api(routeOverride = "login")
suspend fun login(ctx: ApiContext) {
    ctx.res.headers["Cache-Control"] = "no-store"
    if (ctx.req.method != HttpMethod.POST) {
        ctx.res.headers["Allow"] = "POST"
        ctx.res.status = 405
        return
    }

    val secretKey: String? = AUTH_SERVICE_SECRET_KEY.takeIf { it.isNotBlank() }
    if (secretKey == null) {
        ctx.res.body = bodyOf("""{"success":false,"message":"Auth service is not configured."}""", "application/json")
        ctx.res.status = 503
        return
    }

    val requestBody = ctx.req.body
    if (requestBody == null) {
        ctx.res.status = 400
        return
    }

    // Keep the destination server-controlled; never take it from the browser.
    val backendUrl: String = "https://${CRM_BACKEND_WHOLE}}"
        .takeIf { value -> value.replace("https://", "").isNotBlank() } ?: "http://localhost:8080"
    try {
        val request = HttpRequest.newBuilder(URI.create("${backendUrl.trimEnd('/')}/account/auth/login"))
            .timeout(Duration.ofSeconds(15))
            .header("Content-Type", requestBody.contentType)
            .header("Accept", "application/json")
            .header("X-Auth-Service-Secret-Key", secretKey)
            .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody.bytes()))
            .build()

        val response = suspendCoroutine<HttpResponse<ByteArray>> { continuation ->
            loginClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
                .whenComplete { response, error ->
                    if (error != null) continuation.resumeWithException(error)
                    else continuation.resume(response)
                }
        }
        ctx.res.body = bodyOf(
            response.body(),
            response.headers().firstValue("Content-Type").orElse("application/json"),
        )
        ctx.res.status = response.statusCode()
        response.headers().allValues("Set-Cookie").forEach {
            ctx.res.headers.append("Set-Cookie", it)
        }
    } catch (_: Exception) {
        ctx.res.body = bodyOf("""{"success":false,"message":"Unable to contact company login."}""", "application/json")
        ctx.res.status = 502
    }
}
