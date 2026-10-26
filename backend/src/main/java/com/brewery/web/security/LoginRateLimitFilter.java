package com.brewery.web.security;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.TimeMeter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;

/** Per-process IP throttling for direct login requests, before database/password work. */
public final class LoginRateLimitFilter extends OncePerRequestFilter {
    private final Cache<String, Bucket> buckets;
    private final TimeMeter timeMeter;

    public LoginRateLimitFilter() {
        this(TimeMeter.SYSTEM_NANOTIME);
    }

    LoginRateLimitFilter(TimeMeter timeMeter) {
        this.timeMeter = timeMeter;
        this.buckets = Caffeine.newBuilder()
                .maximumSize(10_000)
                .expireAfterAccess(Duration.ofMinutes(10))
                .ticker(timeMeter::currentTimeNanos)
                .build();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !"POST".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Do not trust client-supplied forwarding headers as a bucket identity.
        Bucket bucket = buckets.get(request.getRemoteAddr(), ignored -> Bucket.builder()
                .withCustomTimePrecision(timeMeter)
                .addLimit(limit -> limit.capacity(10).refillGreedy(30, Duration.ofMinutes(1)))
                .build());
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            chain.doFilter(request, response);
            return;
        }

        long retryAfter = Math.max(1L, (probe.getNanosToWaitForRefill() + 999_999_999L) / 1_000_000_000L);
        response.setStatus(429);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Retry-After", Long.toString(retryAfter));
        response.getWriter().write("{\"success\":false,\"message\":\"Too many attempts. Please try again shortly.\",\"data\":null}");
    }
}
