#!/usr/bin/env bash
# Shared setup for the repository's development launch/reload scripts.
set -euo pipefail

cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.."
if [[ ! -f .env ]]; then
    printf 'Missing .env in %s\n' "$PWD" >&2
    exit 1
fi

# Only source a trusted, Bash-compatible .env; accept Windows CRLF endings.
set -a
source <(sed 's/\r$//' .env)
set +a

AUTH_STARTED=0
AUTH_LOG_PID=''

follow_auth_log() {
    tail -n +1 -F auth_service/build/logs/auth.log &
    AUTH_LOG_PID=$!
}

stop_auth_on_exit() {
    local status=$?
    trap - EXIT INT TERM
    if [[ "$AUTH_STARTED" == 1 ]]; then
        printf '\nStopping auth_service...\n'
        if ! bash ./gradlew :auth_service:kobwebStop --console=plain; then
            printf 'Could not stop auth_service. Run: bash ./gradlew :auth_service:kobwebStop\n' >&2
        fi
    fi
    if [[ -n "$AUTH_LOG_PID" ]]; then
        kill "$AUTH_LOG_PID" 2>/dev/null || true
        wait "$AUTH_LOG_PID" 2>/dev/null || true
    fi
    exit "$status"
}

manage_auth_lifetime() {
    trap stop_auth_on_exit EXIT
    trap 'exit 130' INT
    trap 'exit 143' TERM
}
