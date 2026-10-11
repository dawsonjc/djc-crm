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
import com.varabyte.kobweb.api.http.decode

@Api(routeOverride = "/company/add")
suspend fun companyAdd(context: ApiContext) {
    if(context.req.method != HttpMethod.POST) return

    val mapper: ObjectMapper = ObjectMapper();
    val respJson: ObjectNode = mapper.createObjectNode();
    respJson.put("success", false);
    respJson.put("message", "");

    val data: ArrayNode = respJson.putArray("data");

    val companyStore: CompanyStore = CompanyStore()

    val company = context.req.body?.decode<Company>() ?: run {
        context.res.status = 400
        return
    }

    companyStore.add(company)

    val companies: List<Company> = companyStore.getAll()

    companies.stream().forEach {
        data.addPOJO(it);
    }

    respJson.put("success", true);

    context.res.status = 200
    context.res.body = bodyOf(respJson.toString(), "application/json")
}