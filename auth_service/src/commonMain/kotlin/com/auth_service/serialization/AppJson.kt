package com.auth_service.serialization

import kotlinx.serialization.json.Json

/** Shared JSON policy for browser and server serialization. */
val AppJson = Json {
    encodeDefaults = true
    ignoreUnknownKeys = true
}