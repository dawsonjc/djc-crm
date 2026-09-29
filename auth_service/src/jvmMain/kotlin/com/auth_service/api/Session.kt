package com.auth_service.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf

@Api
fun session(ctx: ApiContext) {
    // AuthFilter has verified the auth-service session before this handler can run.
    if (ctx.req.method != HttpMethod.GET) {
        ctx.res.status = 405
        ctx.res.headers["Allow"] = "GET"
        return
    }
    ctx.res.body = bodyOf("""{"authenticated":true}""", "application/json")
}
