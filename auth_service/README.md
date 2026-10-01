# Authentication webapp in Docker

From the repository root, start the main webapp, Cassandra, schema initializer,
and the separate authentication webapp together:

```sh
docker compose up -d --build
```

- Main webapp: http://localhost:8080
- Authentication webapp: http://localhost:8443/login (HTTP)
- Cassandra: localhost:9042

Set `AUTH_SERVICE_SECRET_KEY` in the root `.env` file to a non-empty random
value to enable login. Compose supplies the same value to both webapps and
connects auth to the main app at `http://app:8080`.

```sh
docker compose logs -f app auth_service
docker compose down
```

The auth image builds independently using `docker.settings.gradle`. Its Docker
context includes only the Gradle wrapper, auth build configuration, and auth
sources. The runtime image contains the exported Kobweb site and server.
