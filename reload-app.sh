#!/usr/bin/env bash
# Rebuild the running app, then signal Spring Boot DevTools after success.
set -euo pipefail
source "$(dirname -- "${BASH_SOURCE[0]}")/scripts/dev-common.sh"

bash ./gradlew :frontend:reloadApp --console=plain "$@"
printf 'App rebuilt; a running run-app.sh/run-dev.sh will reload. Refresh your browser.\n'
