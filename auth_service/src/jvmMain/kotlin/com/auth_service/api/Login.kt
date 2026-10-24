package com.auth_service.api

import com.auth_service.http.HttpRequests
import com.auth_service.auth.AuthSessions
import com.auth_service.auth.loginSucceeded
import com.auth_service.http.RequestMethod
import com.auth_service.http.jsonBodyOf
import com.auth_service.model.ApiResponse
import com.auth_service.serialization.AppJson
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf
import com.varabyte.kobweb.api.http.bytes
import kotlinx.serialization.json.JsonElement
import java.net.http.HttpResponse

private val loginClient = HttpRequests()

@Serializable
private data class LoginRequest(
    val username: String? = null,
    val password: String? = null
)


@Api(routeOverride = "login")
suspend fun login(context: ApiContext) {
    context.res.headers["Cache-Control"] = "no-store"
    if (context.req.method != HttpMethod.POST) {
        context.res.headers["Allow"] = "POST"
        context.res.status = 405
        return
    }
    val respJson: ApiResponse<JsonElement> = ApiResponse<JsonElement>()

    val secretKey: String? = AUTH_SERVICE_SECRET_KEY.takeIf { it.isNotBlank() }
    if (secretKey == null) {
        respJson.message = "Auth Service is not configured."
        context.res.body = jsonBodyOf(respJson)
        context.res.status = 503
        return
    }

    val requestBody = context.req.body
    if(requestBody == null) {
        context.res.status = 400
        return
    }

    val loginRequest: LoginRequest = try {
        AppJson.decodeFromString<LoginRequest>(requestBody.bytes().decodeToString())
    } catch (_: SerializationException) {
        respJson.message = "Invalid login request."
        context.res.status = 400
        context.res.body = jsonBodyOf(respJson)
        return
    }

    // Keep the destination server-controlled; never take it from the browser.
    val backendUrl: String = CRM_BACKEND_URL
    try {
        val response: HttpResponse<ByteArray> = loginClient.requestBytes(
            url = "${backendUrl}/auth/login",
            method = RequestMethod.POST,
            body = AppJson.encodeToString(loginRequest).encodeToByteArray(),
            contentType = "application/json; charset=UTF-8",
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
            ),
        )

        if (loginSucceeded(response.statusCode(), response.body())) {
            val token = AuthSessions.current.create(context.req.cookies[AuthSessions.COOKIE_NAME])
            context.res.headers.append("Set-Cookie", AuthSessions.cookie(
                token, context.req.connection.origin.scheme == "https",
            ))
            respJson.success = true
            context.res.body = jsonBodyOf(respJson)
            context.res.status = 200
        } else {
            respJson.message = "Something went wrong!"
            context.res.body = jsonBodyOf(respJson)
            context.res.status = response.statusCode()
        }
        response.headers().allValues("Set-Cookie").forEach {
            context.res.headers.append("Set-Cookie", it)
        }
    } catch (e: Exception) {
        respJson.message = "Something went wrong!"

        context.res.body = jsonBodyOf(respJson)
        context.res.status = 502
    }
}
