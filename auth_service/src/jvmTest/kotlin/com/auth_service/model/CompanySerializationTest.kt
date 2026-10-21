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
    fun `user profile mirrors User bean fields including nested roles and conversations`() {
        val payload = """
            {"userId":"00000000-0000-0000-0000-000000000002",
             "username":"alex","email":"alex@example.com","firstName":"Alex","lastName":"Smith",
             "fullName":"Alex Smith","status":"ACTIVE","accountVerificationStatus":"VERIFIED",
             "createDate":"2026-10-19T00:00:00Z","updateDate":"2026-10-19T01:00:00Z",
             "lastLoginDate":"2026-10-19T02:00:00Z","twoFactorAuthentication":true,
             "languagePreference":"en","timezone":"America/Phoenix",
             "password":"test-placeholder","authApiToken":"00000000-0000-0000-0000-000000000003",
             "friendsConnections":["00000000-0000-0000-0000-000000000004"],"blockedUsers":[],
             "roles":[{"roleName":"Admin","roleNameEnum":"ADMIN","status":"Active"}],
             "conversations":[{"conversationId":"00000000-0000-0000-0000-000000000005",
                "name":"Example","users":["00000000-0000-0000-0000-000000000002"]}]}
        """.trimIndent()
        val user = Json.decodeFromString<CompanyUser>(payload)
        assertEquals("alex", user.username)
        assertEquals("VERIFIED", user.accountVerificationStatus)
        assertEquals(true, user.twoFactorAuthentication)
        assertEquals("Admin", user.roles?.single()?.roleName)
        assertEquals(1, user.conversations?.single()?.users?.size)
        assertNotNull(user.createDate)
        assertEquals(user, Json.decodeFromString<CompanyUser>(Json.encodeToString(user)))
        val edited = user.copy(firstName = "Updated")
        assertEquals(user.roles, edited.roles)
        assertEquals(user.lastLoginDate, edited.lastLoginDate)
    }

    @Test
    fun `partial profile accepts missing optional fields`() {
        val user = Json.decodeFromString<CompanyUser>("""{"username":"alex"}""")
        assertEquals("alex", user.username)
        assertNull(user.userId)
        assertNull(user.twoFactorAuthentication)
    }
}