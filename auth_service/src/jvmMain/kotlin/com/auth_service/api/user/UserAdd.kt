package com.auth_service.api.user

import com.auth_service.http.jsonBodyOf
import com.auth_service.model.ApiResponse
import com.auth_service.model.CompanyUser
import com.auth_service.store.BackendSessionExpiredException
import com.auth_service.store.UserStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.decode

@Api(routeOverride = "/user/add")
suspend fun addUser(context: ApiContext): Unit {
    if(context.req.method != HttpMethod.POST) return
    val respJson: ApiResponse<CompanyUser> = ApiResponse<CompanyUser>();

    val backendSessionId: String = context.req.cookies["JSESSIONID"]?.takeIf { it.isNotBlank() } ?: run {
        respJson.message = "Please sign in again."

        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    }
    val user: CompanyUser = context.req.body?.decode<CompanyUser>() ?: run {
        respJson.message = "Invalid request body."

        context.res.status = 400
        context.res.body = jsonBodyOf(respJson)
        return
    }
    val saved: CompanyUser = try {
        UserStore(logger = context.logger).add(user, backendSessionId)
    } catch (_: BackendSessionExpiredException) {
        respJson.message = "Please sign in again."

        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    }
    respJson.success = true
    respJson.data = saved

    context.res.status = 200
    context.res.body = jsonBodyOf(respJson)
}
