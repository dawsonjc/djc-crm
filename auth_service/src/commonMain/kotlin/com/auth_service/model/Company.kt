package com.auth_service.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.time.Instant

@Serializable
public data class CrmAddress(
    val line1: String?,
    val line2: String?,
    val city: String?,
    val region: String?,
    val postalCode: String?,
    val countryCode: String?
)

@Serializable
public data class Company(
    val workspaceId: String?,
    val companyId: String?,
    val name: String?,
    val normalizedName: String?,
    val companyType: String?,
    val industry: String?,
    val website: String?,
    val emailDomain: String?,
    val phone: String?,
    val ownerUserId: String?,
    val parentCompanyId: Int?,
    val annualRevenue: String?,
    val currency: String?,
    val billingAddress: CrmAddress,
    val shippingAddress: CrmAddress,
    val description: String?,
    val status: String?,
    val customFields: Map<String, String>?,
    val createdAt: Instant?,
    val updateAt: Instant?,
    val createdByUserId: String?,
    val updatedByUserId: String?,

    @Transient
    val companyUsers: List<CompanyUser> = emptyList()
)