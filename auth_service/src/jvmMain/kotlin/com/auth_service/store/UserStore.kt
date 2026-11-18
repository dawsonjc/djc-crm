package com.auth_service.store

import com.auth_service.api.AUTH_SERVICE_SECRET_KEY
import com.auth_service.api.CRM_BACKEND_URL
import com.auth_service.http.HttpRequests
import com.auth_service.http.RequestMethod
import com.auth_service.model.ApiResponse
import com.auth_service.model.CompanyUser
import com.varabyte.kobweb.api.log.Logger
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.http.HttpResponse
import kotlin.uuid.Uuid

private val userClient: HttpRequests = HttpRequests()
private val json = com.auth_service.serialization.AppJson

class UserStore(
    private val backendUrl: String = CRM_BACKEND_URL,
    private val serviceSecretKey: String = AUTH_SERVICE_SECRET_KEY,
    private val client: HttpRequests = userClient,
    private val logger: Logger,
) {
    suspend fun add(backendSessionId: String, user: CompanyUser): CompanyUser {
        val secretKey: String = serviceSecretKey.takeIf { it.isNotBlank() }
            ?: error("AUTH_SERVICE_SECRET_KEY is required to add a company")

        val response: HttpResponse<ByteArray> = client.requestBytes(
            url = "${backendUrl}/auth/user/add",
            method = RequestMethod.POST,
            body = json.encodeToString(user).encodeToByteArray(),
            contentType = "application/json; charset=UTF-8",
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
                "Cookie" to "JSESSIONID=$backendSessionId",
            ),
        )
        logger.debug("UserStore.add: backend returned HTTP ${response.statusCode()}")
        if (response.statusCode() == 401) throw BackendSessionExpiredException()
        check(response.statusCode() in 200..299) {
            "User creation failed with HTTP ${response.statusCode()}"
        }

        val responseText: String = response.body().decodeToString()
        check(responseText.isNotBlank()) {
            "User creation returned an empty response; expected the saved user"
        }
        val body: JsonObject = json.parseToJsonElement(responseText).jsonObject
        check(body["success"]?.jsonPrimitive?.booleanOrNull == true) {
            "User creation response did not indicate success"
        }
        val data: JsonObject = body["data"] as? JsonObject
            ?: error("User creation response is missing a user object in 'data'")

        return json.decodeFromJsonElement<CompanyUser>(data)
    }

    suspend fun delete(backendSessionId: String, companyId: Uuid, userId: Uuid) {
        val secretKey = serviceSecretKey.takeIf { it.isNotBlank() }
            ?: error("AUTH_SERVICE_SECRET_KEY is required to delete a user")

        val response = client.requestBytes(
            url = "${backendUrl.trimEnd('/')}/auth/user/$userId?companyId=$companyId",
            method = RequestMethod.DELETE,
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
                "Cookie" to "JSESSIONID=$backendSessionId",
            ),
        )
        val status = response.statusCode()
        logger.debug("UserStore.delete: backend returned HTTP $status")
        if (status == 401) throw BackendSessionExpiredException()
        if (status in setOf(400, 403, 404, 409)) throw UserDeletionRejectedException(status)
        check(status in 200..299) { "User deletion failed with HTTP $status" }

        val result = json.decodeFromString<ApiResponse<String>>(response.body().decodeToString())
        check(result.success) { "User deletion response did not indicate success" }
    }
}

class UserDeletionRejectedException(val status: Int) : RuntimeException("Could not delete this company user.")
