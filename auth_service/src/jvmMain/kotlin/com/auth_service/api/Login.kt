package com.auth_service.api

import com.auth_service.http.HttpRequests
import com.auth_service.auth.AuthSessions
import com.auth_service.auth.loginSucceeded
import com.auth_service.http.RequestMethod
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf
import com.varabyte.kobweb.api.http.bytes
import java.net.http.HttpResponse

private val loginClient = HttpRequests()

@Api(routeOverride = "login")
suspend fun login(ctx: ApiContext) {
    ctx.res.headers["Cache-Control"] = "no-store"
    if (ctx.req.method != HttpMethod.POST) {
        ctx.res.headers["Allow"] = "POST"
        ctx.res.status = 405
        return
    }
    val mapper: ObjectMapper = ObjectMapper();
    val respJson: ObjectNode = mapper.createObjectNode();
    respJson.put("success", false)
    respJson.put("message", "")
    val data: ObjectNode = respJson.putObject("data")

    val secretKey: String? = AUTH_SERVICE_SECRET_KEY.takeIf { it.isNotBlank() }
    if (secretKey == null) {
        respJson.put("message", "Auth Service is not configured.")
        ctx.res.body = bodyOf(respJson.toString(), "application/json")
        ctx.res.status = 503
        return
    }

    val requestBody = ctx.req.body
    if (requestBody == null) {
        ctx.res.status = 400
        return
    }

    // Keep the destination server-controlled; never take it from the browser.
    val backendUrl: String = CRM_BACKEND_URL
    try {
        val response: HttpResponse<ByteArray> = loginClient.requestBytes(
            url = "${backendUrl}/auth/login",
            method = RequestMethod.POST,
            body = requestBody.bytes(),
            contentType = requestBody.contentType,
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
            ),
        )

        if (loginSucceeded(response.statusCode(), response.body())) {
            val token = AuthSessions.current.create(ctx.req.cookies[AuthSessions.COOKIE_NAME])
            ctx.res.headers.append("Set-Cookie", AuthSessions.cookie(
                token, ctx.req.connection.origin.scheme == "https",
            ))
            respJson.put("success", true)
            ctx.res.body = bodyOf(respJson.toString(), "application/json")
            ctx.res.status = 200
        } else {
            ctx.res.body = bodyOf(
                response.body(),
                response.headers().firstValue("Content-Type").orElse("application/json"),
            )
            ctx.res.status = response.statusCode()
        }
        response.headers().allValues("Set-Cookie").forEach {
            ctx.res.headers.append("Set-Cookie", it)
        }
    } catch (e: Exception) {
        respJson.put("message", e.message)

        ctx.res.body = bodyOf(respJson.toString(), "application/json")
        ctx.res.status = 502
    }
}
