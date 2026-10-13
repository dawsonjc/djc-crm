package com.auth_service.api.company

import com.auth_service.model.Company
import com.auth_service.store.CompanyStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf
import com.varabyte.kobweb.api.http.decode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

@Api(routeOverride = "/company/add")
suspend fun companyAdd(context: ApiContext) {
    if (context.req.method != HttpMethod.POST) return
    val company = context.req.body?.decode<Company>() ?: run {
        context.res.status = 400
        return
    }
    val saved = CompanyStore().add(company)
    val response = buildJsonObject {
        put("success", true)
        put("message", "")
        put("data", Json.encodeToJsonElement(saved))
    }
    context.res.status = 200
    context.res.body = bodyOf(response.toString(), "application/json")
}
