# syntax=docker/dockerfile:1
# Build with the repository root as the context.
FROM eclipse-temurin:21-jdk-jammy AS build

# Kobweb exports pages with headless Chromium; these libraries are build-only.
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        libatomic1 libnss3 libnspr4 libatk1.0-0 libatk-bridge2.0-0 libcups2 \
        libdrm2 libdbus-1-3 libxkbcommon0 libx11-6 libxcomposite1 libxdamage1 \
        libxext6 libxfixes3 libxrandr2 libgbm1 libpango-1.0-0 libcairo2 \
        libasound2 fonts-liberation \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /workspace
COPY gradlew gradlew.bat ./
COPY auth_service/docker.settings.gradle ./settings.gradle
COPY gradle ./gradle
COPY auth_service/build.gradle.kts ./auth_service/build.gradle.kts
COPY auth_service/.kobweb/conf.yaml ./auth_service/.kobweb/conf.yaml
COPY auth_service/src ./auth_service/src

RUN sed -i 's/\r$//' gradlew \
    && chmod +x gradlew \
    && ./gradlew :auth_service:kobwebExport -PkobwebBuildTarget=RELEASE -PkobwebExportLayout=FULLSTACK --no-daemon \
    && ./gradlew :auth_service:kobwebStop --no-daemon \
    && sed -i 's/^"$java_cmd"/exec "$java_cmd"/' auth_service/.kobweb/server/start.sh

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app
COPY --from=build /workspace/auth_service/.kobweb/conf.yaml ./.kobweb/conf.yaml
COPY --from=build /workspace/auth_service/.kobweb/server/server.jar ./.kobweb/server/server.jar
COPY --from=build /workspace/auth_service/.kobweb/server/start.sh ./.kobweb/server/start.sh
COPY --from=build /workspace/auth_service/.kobweb/site ./.kobweb/site

EXPOSE 8443
ENTRYPOINT ["bash", "/app/.kobweb/server/start.sh"]
