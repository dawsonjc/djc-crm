package com.auth_service.api

val AUTH_SERVICE_SECRET_KEY: String = System.getenv("AUTH_SERVICE_SECRET_KEY").orEmpty()
val CRM_BACKEND_PROTOCOL: String = System.getenv("CRM_BACKEND_PROTOCOL")?.takeIf { it.isNotBlank() } ?: "http"
val CRM_BACKEND_HOSTNAME: String = System.getenv("CRM_BACKEND_HOSTNAME")?.takeIf { it.isNotBlank() } ?: "localhost"
val CRM_BACKEND_PORT: String = System.getenv("CRM_BACKEND_PORT")?.takeIf { it.isNotBlank() } ?: "8080"
val CRM_BACKEND_URL: String = System.getenv("CRM_BACKEND_URL")?.takeIf { it.isNotBlank() } ?: "$CRM_BACKEND_PROTOCOL://$CRM_BACKEND_HOSTNAME:$CRM_BACKEND_PORT"
