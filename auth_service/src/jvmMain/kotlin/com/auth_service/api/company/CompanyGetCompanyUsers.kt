package com.auth_service.api.company

import com.auth_service.http.jsonBodyOf
import com.auth_service.model.ApiResponse
import com.auth_service.model.CompanyUsersPage
import com.auth_service.store.BackendSessionExpiredException
import com.auth_service.store.CompanyStore
import com.auth_service.store.CompanyUsersAccessException
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.http.HttpMethod
import kotlin.coroutines.cancellation.CancellationException
import kotlin.uuid.Uuid

@Api(routeOverride = "/companies/{id}/users")
suspend fun CompanyGetCompanyUsers(context: ApiContext) {
    context.res.headers["Cache-Control"] = "no-store"
    fun fail(status: Int, message: String) {
        context.res.status = status
        context.res.body = jsonBodyOf(ApiResponse<CompanyUsersPage>(message = message))
    }

    if (context.req.method != HttpMethod.GET) {
        context.res.headers["Allow"] = "GET"
        return fail(405, "Invalid request method.")
    }
    val sessionId = context.req.cookies["JSESSIONID"]?.takeIf { it.isNotBlank() }
        ?: return fail(401, "Please sign in again.")
    val id = try {
        Uuid.parse(context.req.params["id"] ?: return fail(400, "Company ID is required."))
    } catch (_: IllegalArgumentException) {
        return fail(400, "Invalid company ID.")
    }
    val page = (context.req.queryParams["page"] ?: "0").toIntOrNull()
    val size = (context.req.queryParams["size"] ?: "10").toIntOrNull()
    if (page == null || page < 0 || size == null || size !in 1..500) {
        return fail(400, "Page must be nonnegative and size must be between 1 and 500.")
    }

    val users = try {
        CompanyStore(logger = context.logger).getUsersByCompanyId(id, sessionId, page, size)
    } catch (_: BackendSessionExpiredException) {
        return fail(401, "Please sign in again.")
    } catch (e: CompanyUsersAccessException) {
        return fail(e.status, if (e.status == 404) "Company not found." else "Access denied.")
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        context.logger.warn("Company users request failed: ${e::class.simpleName}")
        return fail(502, "Could not load company users from the CRM backend.")
    }

    context.res.status = 200
    context.res.body = jsonBodyOf(ApiResponse(success = true, data = users))
}
