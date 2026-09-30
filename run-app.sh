#!/usr/bin/env bash
# Start the Spring Boot/JSP/Scala.js application with .env loaded.
set -euo pipefail
source "$(dirname -- "${BASH_SOURCE[0]}")/scripts/dev-common.sh"

printf 'Starting app. Rebuild from another terminal with: bash reload-app.sh\n'
bash ./gradlew :frontend:bootRun "$@"
