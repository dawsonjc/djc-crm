package com.auth_service.api.company

import com.auth_service.store.CompanyStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

@Api(routeOverride = "/company/get/all")
suspend fun companyGet(context: ApiContext) {
    if (context.req.method != HttpMethod.GET) return
    val companies = CompanyStore().getAll()
    val response = buildJsonObject {
        put("success", true)
        put("message", "")
        put("data", Json.encodeToJsonElement(companies))
    }
    context.res.status = 200
    context.res.body = bodyOf(response.toString(), "application/json")
}
