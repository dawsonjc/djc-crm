package com.auth_service.store

import com.auth_service.api.AUTH_SERVICE_SECRET_KEY
import com.auth_service.api.CRM_BACKEND_URL
import com.auth_service.http.HttpRequests
import com.auth_service.http.RequestMethod
import com.auth_service.model.CompanyUser
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.net.http.HttpResponse

private val userClient: HttpRequests = HttpRequests()
private val json = com.auth_service.serialization.AppJson

class UserStore(
    private val backendUrl: String = CRM_BACKEND_URL,
    private val serviceSecretKey: String = AUTH_SERVICE_SECRET_KEY,
    private val client: HttpRequests = userClient,
) {
    suspend fun add(user: CompanyUser, backendSessionId: String): CompanyUser {
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

}