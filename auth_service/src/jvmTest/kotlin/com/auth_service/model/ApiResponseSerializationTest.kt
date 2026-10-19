package com.auth_service.model

import com.auth_service.serialization.AppJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ApiResponseSerializationTest {
    @Test
    fun `default response fields are included`() {
        val body = AppJson.parseToJsonElement(
            AppJson.encodeToString(ApiResponse<List<Company>>())
        ).jsonObject
        assertEquals(JsonPrimitive(false), body["success"])
        assertEquals(JsonPrimitive(""), body["message"])
        assertEquals(JsonNull, body["data"])
    }

    @Test
    fun `typed list response retains its JSON structure`() {
        val response = ApiResponse(success = true, data = listOf(Company(name = "Test")))
        val body = AppJson.parseToJsonElement(AppJson.encodeToString(response)).jsonObject
        assertEquals(JsonPrimitive(true), body["success"])
        assertEquals(JsonPrimitive(""), body["message"])
        assertEquals(JsonPrimitive("Test"), body.getValue("data").jsonArray.single().jsonObject["name"])
    }
}