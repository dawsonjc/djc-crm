package com.auth_service.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Instant
import kotlin.uuid.Uuid

@Serializable
data class CrmAddress(
    val line1: String? = null,
    val line2: String? = null,
    val city: String? = null,
    val region: String? = null,
    val postalCode: String? = null,
    val countryCode: String? = null
)

@Serializable
data class Company(
    val companyId: Uuid? = null,
    val name: String? = null,
    val legalName: String? = null,
    val billingEmail: String? = null,
    val phone: String? = null,
    val website: String? = null,
    val primaryAdminUserId: Uuid? = null,
    val billingAddress: CrmAddress? = null,
    val status: String? = "Active",
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
    val createdByUserId: Uuid? = null,
    val updatedByUserId: Uuid? = null,

    @Transient
    val companyUsers: List<CompanyUser> = emptyList()
)
