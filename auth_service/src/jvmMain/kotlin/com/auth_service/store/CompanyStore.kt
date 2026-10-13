package com.auth_service.store

import com.auth_service.api.AUTH_SERVICE_SECRET_KEY
import com.auth_service.api.CRM_BACKEND_URL
import com.auth_service.http.HttpRequests
import com.auth_service.http.RequestMethod
import com.auth_service.model.Company
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import java.net.http.HttpResponse

private val companyClient: HttpRequests = HttpRequests()
private val json: Json = Json {
    ignoreUnknownKeys = true
}

class CompanyStore {
    suspend fun add(company: Company): Company {
        val secretKey: String = AUTH_SERVICE_SECRET_KEY.takeIf { it.isNotBlank() }
            ?: error("AUTH_SERVICE_SECRET_KEY is required to add a company")

        val response: HttpResponse<ByteArray> = companyClient.requestBytes(
            url = "${CRM_BACKEND_URL}/auth/company/add",
            method = RequestMethod.POST,
            body = json.encodeToString(company).encodeToByteArray(),
            contentType = "application/json; charset=UTF-8",
            headers = mapOf(
                "Accept" to "application/json",
                "X-Auth-Service-Secret-Key" to secretKey,
            ),
        )
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

    suspend fun getAll(): List<Company> {
        val secretKey: String? = AUTH_SERVICE_SECRET_KEY.takeIf { it.isNotBlank() }
        if(secretKey == null) {
            println("SECRET KEY NULL ON CompanyStore.getAll")
            return listOf()
        }

        val backendUrl: String = CRM_BACKEND_URL

        val response: HttpResponse<ByteArray>
        try {
            response = companyClient.requestBytes(
                url = "${backendUrl}/auth/company/all",
                method = RequestMethod.GET,
                headers = mapOf(
                    "Accept" to "application/json",
                    "X-Auth-Service-Secret-Key" to secretKey,
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            println("ERROR ON CompanyStore.getAll ${e.message}")
            return listOf()
        }
        if(response.statusCode() !in 200..299) {
            println("ERROR ON CompanyStore.getAll ${response.statusCode()}")
            return listOf();
        }

        val body: JsonObject = json.parseToJsonElement(
            response.body().decodeToString()
        ).jsonObject

        val data = body["data"]
            ?: error("Company response is missing 'data'")

        return json.decodeFromJsonElement<List<Company>>(data)
    }
}