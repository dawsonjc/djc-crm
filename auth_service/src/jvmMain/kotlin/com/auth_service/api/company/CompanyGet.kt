package com.auth_service.api.company

import com.auth_service.model.Company
import com.auth_service.store.CompanyStore
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.bodyOf

@Api(routeOverride = "/company/get/all")
suspend fun companyGet(context: ApiContext) {
    if(context.req.method != HttpMethod.GET) return
    val mapper: ObjectMapper = ObjectMapper();
    val respJson: ObjectNode = mapper.createObjectNode();
    respJson.put("success", false);
    respJson.put("message", "");

    val data: ArrayNode = respJson.putArray("data");

    val companyStore: CompanyStore = CompanyStore()
    val companies: List<Company> = companyStore.getAll()

    companies.stream().forEach {
        data.addPOJO(it);
    }

    respJson.put("success", true);

    context.res.status = 200
    context.res.body = bodyOf(respJson.toString(), "application/json")
}