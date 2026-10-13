package com.auth_service.model

import kotlinx.serialization.Serializable
import kotlin.time.Instant
import kotlin.uuid.Uuid

/** Company membership; profile, credentials and verification remain on the user. */
@Serializable
data class CompanyUser(
    val companyId: Uuid,
    val userId: Uuid,
    val role: String,
    // Uses the backend RecordStatus display values: Active or Disabled.
    val status: String = "Active",
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null
)
