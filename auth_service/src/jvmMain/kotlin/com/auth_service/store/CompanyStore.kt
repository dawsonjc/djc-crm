package com.auth_service.store

import com.auth_service.api.AUTH_SERVICE_SECRET_KEY
import com.auth_service.api.CRM_BACKEND_URL
import com.auth_service.http.HttpRequests
import com.auth_service.http.RequestMethod
import com.auth_service.model.Company
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.varabyte.kobweb.api.http.bytes
import java.net.http.HttpResponse

private val companyClient: HttpRequests = HttpRequests()

class CompanyStore {
    suspend fun add(company: Company): Unit {

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

        val mapper: ObjectMapper = ObjectMapper();

        val res: ByteArray = response.body()
        val body: ObjectNode = mapper.readTree(res) as ObjectNode;
        val data =  body.get("data") as ArrayNode

        println("Success with data ${body.toString()}")

        return ;
    }
}