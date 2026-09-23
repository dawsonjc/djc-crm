package com.auth_service.api

val AUTH_SERVICE_SECRET_KEY: String = System.getenv("AUTH_SERVICE_SECRET_KEY");
val CRM_BACKEND_HOSTNAME: String = System.getenv("CRM_BACKEND_HOSTNAME");
val CRM_BACKEND_PORT: String = System.getenv("CRM_BACKEND_PORT");
val CRM_BACKEND_URL: String = ("https://$CRM_BACKEND_HOSTNAME:$CRM_BACKEND_PORT").takeIf { it != "https://:" } ?: "https://localhost:8080";