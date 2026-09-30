#!/usr/bin/env bash
# Start Kobweb and own its lifetime until Ctrl+C.
set -euo pipefail
source "$(dirname -- "${BASH_SOURCE[0]}")/scripts/dev-common.sh"
manage_auth_lifetime

bash ./gradlew :auth_service:kobwebStart --console=plain "$@"
printf 'Auth is running. Rebuild with: bash reload-auth.sh (Ctrl+C here stops auth)\n'
# Kobweb runs separately after Gradle exits; keep this launcher available to stop it.
while true; do sleep 1; done
