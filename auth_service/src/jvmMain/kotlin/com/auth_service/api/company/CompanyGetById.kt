package com.auth_service.api.company

import com.auth_service.model.ApiResponse
import com.auth_service.model.Company
import com.auth_service.store.CompanyStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.auth_service.http.jsonBodyOf
import com.auth_service.serialization.AppJson
import kotlin.uuid.Uuid

@Api(routeOverride = "/company/get/{id}")
suspend fun getCompanyById(context: ApiContext): Unit {
    if(context.req.method != HttpMethod.GET) return;
    val respJson: ApiResponse<Company> = ApiResponse<Company>();

    val backendSessionId: String = context.req.cookies["JSESSIONID"]?.takeIf { it.isNotBlank() } ?: run {
        respJson.message = "Please sign in again."

        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    }

    val idParam = context.req.params["id"] ?: return;

    val id: Uuid = try {
        Uuid.parse(idParam)
    } catch(e: IllegalArgumentException) {
        respJson.message = "Invalid ID format."

        context.res.status = 400
        context.res.body = jsonBodyOf(respJson)
        return
    }

    val company: Company = CompanyStore().getById(id, backendSessionId)

    respJson.success = true
    respJson.data = company;
    context.res.body = jsonBodyOf(respJson)
}