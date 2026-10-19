package com.auth_service.api.company

import com.auth_service.model.ApiResponse
import com.auth_service.model.Company
import com.auth_service.store.CompanyStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.auth_service.http.jsonBodyOf
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.put

@Api(routeOverride = "/company/get/all")
suspend fun companyGet(context: ApiContext) {
    val response: ApiResponse<List<Company>> = ApiResponse<List<Company>>();
    if (context.req.method != HttpMethod.GET) {
        context.res.status = 405;
        context.res.body = jsonBodyOf(response)
        return
    }
    val companies = CompanyStore().getAll()

    response.success = true
    response.data = companies;

    context.res.status = 200
    context.res.body = jsonBodyOf(response)
}
