#!/usr/bin/env bash
# Check compilation without starting services or running tests.
set -euo pipefail

usage() {
    cat <<'EOF'
Usage: ./test-compile.sh [all | backend | frontend | scala-js | app | auth] ...
  all       Compile app and auth (default)
  backend   Compile only the Spring Boot backend
  frontend  Compile frontend classes and their backend dependency
  scala-js  Compile and link the app's Scala.js browser code (alias: scala_js)
  app       Compile frontend and backend, and link Scala.js
  auth      Compile the Kobweb JVM and browser code (alias: auth_service)
EOF
}

if (( $# == 0 )); then
    set -- all
fi

backend=false
frontend=false
scala_js=false
auth=false
for target in "$@"; do
    case "$target" in
        all) backend=true; frontend=true; scala_js=true; auth=true ;;
        backend) backend=true ;;
        frontend) frontend=true ;;
        scala-js|scala_js) scala_js=true ;;
        app) backend=true; frontend=true; scala_js=true ;;
        auth|auth_service) auth=true ;;
        -h|--help) usage; exit 0 ;;
        *)
            printf 'Unknown compile target: %s\n' "$target" >&2
            usage >&2
            exit 2
            ;;
    esac
done

tasks=()
if "$backend"; then
    tasks+=(:backend:classes)
fi
if "$frontend"; then
    tasks+=(:frontend:classes)
fi
if "$scala_js"; then
    tasks+=(:scala_js:link)
fi
if "$auth"; then
    tasks+=(:auth_service:compileKotlinJvm :auth_service:compileKotlinJs)
fi

cd -- "$(dirname -- "${BASH_SOURCE[0]}")"
exec bash ./gradlew --console=plain "${tasks[@]}"
