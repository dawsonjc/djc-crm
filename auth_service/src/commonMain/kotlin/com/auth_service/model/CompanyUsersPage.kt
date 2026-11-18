package com.auth_service.model

import kotlinx.serialization.Serializable

/** Payload inside ApiResponse.data for GET /api/companies/{id}/users?page=0&size=10. */
@Serializable
data class CompanyUsersPage(
    val content: List<CompanyUser>,
    val number: Int,
    val size: Int,
    val last: Boolean,
    // Optional: a Spring Slice need not calculate totals.
    val totalElements: Long? = null,
    val totalPages: Int? = null,
)

