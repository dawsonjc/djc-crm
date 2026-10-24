package com.auth_service.store

import com.auth_service.api.AUTH_SERVICE_SECRET_KEY
import com.auth_service.api.CRM_BACKEND_URL
import com.auth_service.http.HttpRequests
import com.auth_service.http.RequestMethod
import com.auth_service.model.ApiResponse
import com.auth_service.model.Company
import com.varabyte.kobweb.api.log.Logger
import kotlinx.serialization.encodeToString
import com.auth_service.serialization.AppJson
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.net.http.HttpResponse
import kotlin.uuid.Uuid

private val companyClient: HttpRequests = HttpRequests()
private val json = com.auth_service.serialization.AppJson

class BackendSessionExpiredException : RuntimeException("Please sign in again.")

class CompanyStore(
    private val backendUrl: String = CRM_BACKEND_URL,
    private val serviceSecretKey: String = AUTH_SERVICE_SECRET_KEY,
    private val client: HttpRequests = companyClient,
    private val logger: Logger,
) {
    suspend fun add(company: Company, backendSessionId: String): Company {
        val secretKey: String = serviceSecretKey.takeIf { it.isNotBlank() }
            ?: error("AUTH_SERVICE_SECRET_KEY is required to add a company")

        val response: HttpResponse<ByteArray> = client.requestBytes(
            url = "${backendUrl}/auth/company/add",
            method = RequestMethod.POST,
            body = json.encodeToString(company).encodeToByteArray(),
            contentType = "application/json; charset=UTF-8",
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
                "Cookie" to "JSESSIONID=$backendSessionId",
            ),
        )
        if (response.statusCode() == 401) throw BackendSessionExpiredException()
        check(response.statusCode() in 200..299) {
            "Company creation failed with HTTP ${response.statusCode()}"
        }

        val responseText: String = response.body().decodeToString()
        check(responseText.isNotBlank()) {
            "Company creation returned an empty response; expected the saved company"
        }
        val body: JsonObject = json.parseToJsonElement(responseText).jsonObject
        check(body["success"]?.jsonPrimitive?.booleanOrNull == true) {
            "Company creation response did not indicate success"
        }
        val data: JsonObject = body["data"] as? JsonObject
            ?: error("Company creation response is missing a company object in 'data'")

        return json.decodeFromJsonElement<Company>(data)
    }

    suspend fun getAll(backendSessionId: String): List<Company> {
        val secretKey: String? = serviceSecretKey.takeIf { it.isNotBlank() }
        if(secretKey == null) {
            logger.error("CompanyStore.getAll: AUTH_SERVICE_SECRET_KEY is not configured")
            return listOf()
        }

        val response: HttpResponse<ByteArray>
        try {
            response = client.requestBytes(
                url = "${backendUrl}/auth/company/all",
                method = RequestMethod.GET,
                headers = mapOf(
                    "Accept" to "application/json",
                    "X-Auth-Service-Secret-Key" to secretKey,
                    "Cookie" to "JSESSIONID=$backendSessionId",
                )
            )
        } catch (e: Exception) {
            logger.error("CompanyStore.getAll: backend request failed\n${e.stackTraceToString()}")
            return listOf()
        }

        val body: JsonObject = json.parseToJsonElement(
            response.body().decodeToString()
        ).jsonObject

        if(response.statusCode() !in 200..299) {
            logger.warn("CompanyStore.getAll: backend returned HTTP ${response.statusCode()} with $body")
            return listOf();
        }



        val data = body["data"]
            ?: error("Company response is missing 'data'")

        return json.decodeFromJsonElement<List<Company>>(data)
    }

    suspend fun getById(id: Uuid, backendSessionId: String): Company {
        val secretKey: String = serviceSecretKey.takeIf { it.isNotBlank() }
            ?: error("AUTH_SERVICE_SECRET_KEY is required to add a company")

        val response: HttpResponse<ByteArray> = client.requestBytes(
            url = "${backendUrl}/auth/company/$id",
            method = RequestMethod.GET,
            contentType = "application/json; charset=UTF-8",
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
                "Cookie" to "JSESSIONID=$backendSessionId",
            ),
        )

        val body: JsonObject = json.parseToJsonElement(
            response.body().decodeToString()
        ).jsonObject

        val data = body["data"]
            ?: error("Company response is missing 'data'")

        return json.decodeFromJsonElement<Company>(data)
    }
}
