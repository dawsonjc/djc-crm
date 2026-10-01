package com.auth_service.auth

import com.fasterxml.jackson.databind.ObjectMapper
import java.security.SecureRandom
import java.time.Clock
import java.time.Duration
import java.time.Instant
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap

/** Local, opaque sessions. Restarting the auth service signs everyone out. */
class AuthSessions(private val clock: Clock = Clock.systemUTC()) {
    private val sessions = ConcurrentHashMap<String, Instant>()
    private val random = SecureRandom()

    fun create(previous: String? = null): String {
        if (previous != null) sessions.remove(previous)
        val now = clock.instant()
        sessions.entries.removeIf { !it.value.isAfter(now) }
        val token: String = Base64.getUrlEncoder().withoutPadding()
            .encodeToString(ByteArray(32).also(random::nextBytes))
        sessions[token] = now.plus(LIFETIME)
        return token
    }

    fun isAuthenticated(token: String?): Boolean {
        if (token == null) return false
        val expires: Instant = sessions[token] ?: return false
        if (expires.isAfter(clock.instant())) return true
        sessions.remove(token, expires)
        return false
    }

    companion object {
        const val COOKIE_NAME = "AUTH_SERVICE_SESSION"
        val LIFETIME: Duration = Duration.ofMinutes(30)
        val current = AuthSessions()

        fun cookie(token: String, secure: Boolean): String =
            "$COOKIE_NAME=$token; Path=/; HttpOnly; SameSite=Lax; Max-Age=${LIFETIME.seconds}" +
                if (secure) "; Secure" else ""
    }
}

internal fun loginSucceeded(status: Int, body: ByteArray): Boolean {
    if (status !in 200..299) return false
    return try {
        ObjectMapper().readTree(body)?.path("success")?.asBoolean(false) == true
    } catch (_: Exception) {
        false
    }
}
