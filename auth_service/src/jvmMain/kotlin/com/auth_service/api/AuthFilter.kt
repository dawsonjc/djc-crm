package com.auth_service.api

import com.auth_service.auth.AuthSessions
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.bodyOf
import com.varabyte.kobweb.api.intercept.ApiInterceptor
import com.varabyte.kobweb.api.intercept.ApiInterceptorContext

@ApiInterceptor
suspend fun authFilter(ctx: ApiInterceptorContext): Response {
    if (ctx.path == "/login") return ctx.dispatcher.dispatch()

    val authenticated = AuthSessions.current.isAuthenticated(ctx.req.cookies[AuthSessions.COOKIE_NAME])
    if (!authenticated) return Response().apply {
        status = 401
        headers["Cache-Control"] = "no-store"
        body = bodyOf("""{"authenticated":false,"message":"Please sign in."}""", "application/json")
    }
    return ctx.dispatcher.dispatch().also { it.headers["Cache-Control"] = "no-store" }
}
