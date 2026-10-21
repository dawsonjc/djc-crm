package com.auth_service.api.user

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext

@Api(routeOverride = "/user/get/{id}")
suspend fun getUser(context: ApiContext): Unit {
    

}