package com.auth_service.model

import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CompanySerializationTest {
    @Test
    fun `tenant response preserves identifiers dates and RecordStatus display values`() {
        val payload = """
            {"companyId":"00000000-0000-0000-0000-000000000001","name":"Example",
             "legalName":"Example LLC","billingEmail":"billing@example.com","status":"Disabled",
             "billingAddress":{"city":"Phoenix"},"createdAt":"2026-10-12T00:00:00Z",
             "updatedAt":"2026-10-12T01:00:00Z"}
        """.trimIndent()
        val company = Json.decodeFromString<Company>(payload)
        assertEquals("Disabled", company.status)
        assertEquals("Phoenix", company.billingAddress?.city)
        assertNotNull(company.updatedAt)
        val encoded = Json.encodeToString(company)
        assertTrue(encoded.contains("\"companyId\":\"00000000-0000-0000-0000-000000000001\""))
        assertFalse(encoded.contains("workspaceId"))
        assertFalse(encoded.contains("shippingAddress"))
        assertFalse(encoded.contains("companyUsers"))
        assertEquals(company, Json.decodeFromString<Company>(encoded))
    }

    @Test
    fun `company membership is separate from profile and verification`() {
        val membership = Json.decodeFromString<CompanyUser>("""
            {"companyId":"00000000-0000-0000-0000-000000000001",
             "userId":"00000000-0000-0000-0000-000000000002",
             "role":"Admin","status":"Disabled"}
        """.trimIndent())
        assertEquals("Disabled", membership.status)
        assertEquals("Admin", membership.role)
        assertEquals(membership, Json.decodeFromString<CompanyUser>(Json.encodeToString(membership)))
    }
}
