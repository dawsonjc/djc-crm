#!/usr/bin/env bash
# Load the repository's trusted, Bash-compatible .env and start both apps.
set -euo pipefail

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"

if [[ ! -f .env ]]; then
    printf 'Missing .env in %s\n' "$PWD" >&2
    exit 1
fi

# Export assignments to Gradle and accept Windows CRLF line endings.
set -a
source <(sed 's/\r$//' .env)
set +a

# Kobweb starts a separate server process, so explicitly stop it on exit.
cleanup() {
    local status=$?
    trap - EXIT INT TERM
    printf '\nStopping auth_service...\n'
    if ! bash ./gradlew :auth_service:kobwebStop --console=plain; then
        printf 'Could not stop auth_service. Run: bash ./gradlew :auth_service:kobwebStop\n' >&2
    fi
    exit "$status"
}

trap cleanup EXIT
trap 'exit 130' INT
trap 'exit 143' TERM

printf 'Starting auth_service...\n'
bash ./gradlew :auth_service:authDev --console=plain

# Keep frontend in the foreground so terminal Ctrl+C reaches Gradle.
# Extra arguments belong to bootRun (for example --args=--server.port=8081).
printf 'Starting frontend...\n'
bash ./gradlew :frontend:bootRun "$@"
