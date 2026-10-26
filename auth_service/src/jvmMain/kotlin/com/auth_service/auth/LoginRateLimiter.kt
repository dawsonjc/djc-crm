package com.auth_service.auth

import com.auth_service.http.jsonBodyOf
import com.auth_service.model.ApiResponse
import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import com.varabyte.kobweb.api.http.HttpMethod
import com.varabyte.kobweb.api.http.Request
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.bodyOf
import io.github.bucket4j.Bucket
import io.github.bucket4j.TimeMeter
import kotlinx.serialization.json.JsonElement
import java.time.Duration

/** Per-process client-IP buckets at the public entry point, before backend forwarding. */
internal class LoginRateLimiter(private val timeMeter: TimeMeter = TimeMeter.SYSTEM_NANOTIME) {
    private val buckets: Cache<String, Bucket> = Caffeine.newBuilder()
        .maximumSize(10_000)
        .expireAfterAccess(Duration.ofMinutes(10))
        .ticker(timeMeter::currentTimeNanos)
        .build()

    fun check(request: Request): Response? {
        if (request.method != HttpMethod.POST) return null
        // Use the socket peer, not origin or browser-supplied forwarding headers.
        val bucket = buckets.get(request.connection.local.remoteAddress) {
            Bucket.builder()
                .withCustomTimePrecision(timeMeter)
                .addLimit { limit -> limit.capacity(10).refillGreedy(30, Duration.ofMinutes(1)) }
                .build()
        }
        val probe = bucket.tryConsumeAndReturnRemaining(1)
        if (probe.isConsumed) return null
        val retryAfter = maxOf(1L, (probe.nanosToWaitForRefill + 999_999_999L) / 1_000_000_000L)
        return Response().apply {
            val response: ApiResponse<JsonElement> = ApiResponse<JsonElement>(
                success = false,
                message = "Too many attempts. Please try again shortly.",
                data = null
            )

            status = 429
            headers["Retry-After"] = retryAfter.toString()
            headers["Cache-Control"] = "no-store"
            body = jsonBodyOf(response)
        }
    }
}
