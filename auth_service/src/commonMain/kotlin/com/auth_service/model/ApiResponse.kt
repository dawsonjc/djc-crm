package com.auth_service.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    var success: Boolean = false,
    var message: String = "",
    var data: T? = null
)
