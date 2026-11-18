package com.auth_service.api.company

import com.auth_service.http.jsonBodyOf
import com.auth_service.model.ApiResponse
import com.auth_service.store.BackendSessionExpiredException
import com.auth_service.store.UserDeletionRejectedException
import com.auth_service.store.UserStore
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import kotlin.coroutines.cancellation.CancellationException
import kotlin.uuid.Uuid

@Api(routeOverride = "/companies/{id}/users/{userId}")
suspend fun deleteCompanyUser(context: ApiContext) {
    val respJson: ApiResponse<String> = ApiResponse<String>()
    context.res.headers["Cache-Control"] = "no-store"

    if (context.req.method != HttpMethod.DELETE) {
        respJson.message = "Invalid request method."
        context.res.headers["Allow"] = "DELETE"
        context.res.status = 405
        context.res.body = jsonBodyOf(respJson)
        return
    }

    val backendSessionId = context.req.cookies["JSESSIONID"]?.takeIf { it.isNotBlank() } ?: run {
        respJson.message = "Please sign in again."
        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    }

    val companyId: Uuid
    val userId: Uuid
    try {
        companyId = Uuid.parse(context.req.params["id"].orEmpty())
        userId = Uuid.parse(context.req.params["userId"].orEmpty())
    } catch (_: IllegalArgumentException) {
        respJson.message = "Invalid company or user ID."
        context.res.status = 400
        context.res.body = jsonBodyOf(respJson)
        return
    }

    try {
        UserStore(logger = context.logger).delete(backendSessionId, companyId, userId)
    } catch (_: BackendSessionExpiredException) {
        respJson.message = "Please sign in again."
        context.res.status = 401
        context.res.body = jsonBodyOf(respJson)
        return
    } catch (e: UserDeletionRejectedException) {
        respJson.message = "Could not delete this company user."
        context.res.status = e.status
        context.res.body = jsonBodyOf(respJson)
        return
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        context.logger.warn("Company user deletion failed: ${e::class.simpleName}")
        respJson.message = "Could not delete user. Refresh the list before retrying."
        context.res.status = 502
        context.res.body = jsonBodyOf(respJson)
        return
    }

    respJson.success = true
    respJson.message = "User deleted."
    context.res.status = 200
    context.res.body = jsonBodyOf(respJson)
}
