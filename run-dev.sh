#!/usr/bin/env bash
# Load .env and start both services, keeping app in the foreground.
set -euo pipefail
source "$(dirname -- "${BASH_SOURCE[0]}")/scripts/dev-common.sh"
manage_auth_lifetime

printf 'Starting auth_service...\n'
bash ./gradlew :auth_service:kobwebStart --console=plain

# Extra arguments belong to bootRun (for example --args=--server.port=8081).
printf 'Starting app. Rebuild from another terminal: bash reload-app.sh / bash reload-auth.sh\n'
bash ./gradlew :frontend:bootRun "$@"
