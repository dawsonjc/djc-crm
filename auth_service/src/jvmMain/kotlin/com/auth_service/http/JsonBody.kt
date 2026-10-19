package com.auth_service.http

import com.auth_service.serialization.AppJson
import com.varabyte.kobweb.api.http.Body
import com.varabyte.kobweb.api.http.bodyOf
import kotlinx.serialization.encodeToString

/** Use for typed JSON responses; Kobweb's default bodyOf uses its own Json instance. */
inline fun <reified T> jsonBodyOf(value: T): Body =
    bodyOf(AppJson.encodeToString(value), "application/json")