package com.auth_service.auth

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Clock
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class AuthSessionsTest {
    private class TestClock(var now: Instant = Instant.parse("2026-01-01T00:00:00Z")) : Clock() {
        override fun instant() = now
        override fun getZone(): ZoneId = ZoneOffset.UTC
        override fun withZone(zone: ZoneId): Clock = this
    }

    @Test
    fun `missing forged expired and restarted sessions fail closed`() {
        val clock = TestClock()
        val sessions = AuthSessions(clock)
        assertFalse(sessions.isAuthenticated(null))
        assertFalse(sessions.isAuthenticated("forged"))
        val token = sessions.create()
        assertTrue(sessions.isAuthenticated(token))
        assertFalse(AuthSessions(clock).isAuthenticated(token))
        clock.now = clock.now.plus(AuthSessions.LIFETIME)
        assertFalse(sessions.isAuthenticated(token))
    }

    @Test
    fun `successful reauthentication rotates the token`() {
        val sessions = AuthSessions()
        val old = sessions.create()
        val fresh = sessions.create(old)
        assertNotEquals(old, fresh)
        assertFalse(sessions.isAuthenticated(old))
        assertTrue(sessions.isAuthenticated(fresh))
    }

    @Test
    fun `only backend login success establishes a session`() {
        assertTrue(loginSucceeded(200, byteArrayOf()))
        assertTrue(loginSucceeded(200, """{"success":true}""".toByteArray()))
        assertFalse(loginSucceeded(200, """{"success":false}""".toByteArray()))
        assertFalse(loginSucceeded(200, "<html>Login</html>".toByteArray()))
        assertFalse(loginSucceeded(302, byteArrayOf()))
        assertFalse(loginSucceeded(401, byteArrayOf()))
        assertFalse(loginSucceeded(500, byteArrayOf()))
    }

    @Test
    fun `session cookie restricts script access and uses secure transport on https`() {
        val cookie = AuthSessions.cookie("test", true)
        assertTrue(cookie.contains("HttpOnly"))
        assertTrue(cookie.contains("SameSite=Lax"))
        assertTrue(cookie.contains("Max-Age=1800"))
        assertTrue(cookie.contains("; Secure"))
        assertFalse(AuthSessions.cookie("test", false).contains("; Secure"))
    }
}
