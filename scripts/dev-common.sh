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

stop_auth_on_exit() {
    local status=$?
    trap - EXIT INT TERM
    printf '\nStopping auth_service...\n'
    if ! bash ./gradlew :auth_service:kobwebStop --console=plain; then
        printf 'Could not stop auth_service. Run: bash ./gradlew :auth_service:kobwebStop\n' >&2
    fi
    exit "$status"
}

manage_auth_lifetime() {
    trap stop_auth_on_exit EXIT
    trap 'exit 130' INT
    trap 'exit 143' TERM
}
