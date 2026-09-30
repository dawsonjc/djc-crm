#!/usr/bin/env bash
# Kobweb rebuilds its development output and reuses the running server.
set -euo pipefail
source "$(dirname -- "${BASH_SOURCE[0]}")/scripts/dev-common.sh"

bash ./gradlew :auth_service:kobwebStart -PkobwebReuseServer=true --console=plain "$@"
printf 'Auth rebuilt. If no server was running, Kobweb started one.\n'
