package com.auth_service.api.company

import com.auth_service.model.ApiResponse
import com.auth_service.model.Company
import com.auth_service.store.CompanyStore
import com.auth_service.store.BackendSessionExpiredException
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.auth_service.http.jsonBodyOf
import com.varabyte.kobweb.api.http.decode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

@Api(routeOverride = "/company/add")
suspend fun companyAdd(context: ApiContext) {
    if (context.req.method != HttpMethod.POST) return
    val respJson: ApiResponse<Company> = ApiResponse<Company>();

    val backendSessionId = context.req.cookies["JSESSIONID"]?.takeIf { it.isNotBlank() } ?: run {
        respJson.message = "Please sign in again."

        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    }
    val company = context.req.body?.decode<Company>() ?: run {
        context.res.status = 400
        return
    }
    val saved: Company = try {
        CompanyStore().add(company, backendSessionId)
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
