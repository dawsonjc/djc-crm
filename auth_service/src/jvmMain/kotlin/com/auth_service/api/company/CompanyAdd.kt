package com.auth_service.api.company

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod

@Api(routeOverride = "/company/add")
fun companyAdd(context: ApiContext) {
    if (context.req.method != HttpMethod.POST) return
}