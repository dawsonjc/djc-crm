# Technology upgrade — October 26, 2026

The project targets current stable releases verified against official package registries and release documentation on this date. Preview, milestone, nightly and release-candidate versions were excluded. Spring's remaining transitive dependencies follow its BOM rather than being upgraded independently.

## Main changes

| Component | Previous | Updated |
|---|---|---|
| Application JDK | 21 | 27 (Temurin 27+35) |
| Gradle | 9.7.0 | 9.8.2; wrapper regenerated, distribution SHA-256 pinned |
| Spring Boot | 3.5.5 | 4.1.2 |
| Embedded Tomcat / Servlet | Explicit Jasper 10.1.30 / Servlet 3.1 descriptor | Boot-managed Tomcat 11.0.27 / Servlet 6.1 descriptor |
| Jackson | Backend Boot-managed 2.x; auth 2.22.2 | 3.2.4, with shared 2.x annotations aligned through Jackson 2.22.4 BOM |
| Kotlin / Compose compiler | 2.4.10 | 2.4.21 |
| Compose runtime / HTML | 1.12.0 / 1.11.1 | 1.12.1 / 1.12.2 |
| Kotlin serialization | 1.9.0 | 1.12.0 |
| Browser-build Node.js | Kotlin default | 26.12.1, downloaded by Gradle |
| Scala | 3.7.3 | 3.10.0 |
| Scala.js Gradle plugin | 1.0.4 | 2.3.0 |
| Caffeine | 3.2.2 / Boot-managed | 3.3.1 |
| JUnit Jupiter / Platform | 5.12.2 / 1.12.2 in auth | 6.1.3 |
| Cassandra image | 4.1.7 | 5.0.9 for new deployments; retained data needs a separate upgrade |
| Cassandra Java driver | Previous Boot-managed version | 4.19.4, managed by Boot 4.1.2 |
| jQuery | 3.7.1 | 4.0.0 |
| DataTables | 2.3.4 | 3.1.4 |
| Tailwind browser | Unversioned legacy CDN runtime | 4.3.3; colors migrated to CSS `@theme` |
| Font Awesome | 6.5.1 | 7.3.1 |
| DOMPurify | Floating 3.x CDN | 3.4.17, pinned with integrity hash |

Kobweb 0.25.1, Scala.js 1.22.0, scalajs-dom 2.8.1, Udash jQuery 3.4.0, Scala.js jsdom environment 1.1.1, Bucket4j 8.21.0, jBCrypt 0.4, JSTL API 3.0.2, Bootstrap 5.3.8 and dependency-management plugin 1.1.7 were already the latest stable releases for their selected artifacts. Yarn remains the latest supported Classic release, 1.22.22; migrating to Yarn's newer incompatible major is not part of the Kotlin plugin's supported setup.

## Compatibility adaptations

- Backend and auth imports use Jackson 3's `tools.jackson.databind` namespace. Jackson annotations remain under `com.fasterxml.jackson.annotation`. JSON parsing now catches Jackson's runtime exception type; mapper construction in the DTO test uses the Jackson 3 builder.
- Spring MVC uses the Boot 4 `spring-boot-starter-webmvc` starter. The obsolete explicit Jasper version is removed so Tomcat components stay aligned. Duplicate rate-limiter/cache declarations were removed.
- Both Spring modules have explicit Java 27 toolchains. Kotlin 2.4.21 supports JVM bytecode through version 26, so auth deliberately compiles to JVM 26 while building/running on JDK 27. Its Java task release is aligned to 26 to avoid inconsistent targets.
- The Spring Data mapping test configures the mapping context's simple-type holder and the converter's custom conversions using the current API.
- Container build/runtime bases use `eclipse-temurin:27-jdk-resolute` / `27-jre-resolute`. Adoptium no longer publishes Java 27 on Jammy/Noble. Auth's Chromium dependencies use Ubuntu's current `t64` package names.
- Browser URLs are pinned. Integrity hashes cover updated assets. Tailwind 4's CSS theme preserves the project's custom color names.
- Compose forwards the registration service's existing URL/signing-secret settings to the app. Provide real secret values in your local/deployment environment.

## Local development

Install a JDK 27 distribution and set `JAVA_HOME`, the IDE project SDK, and the IDE Gradle JVM accordingly. The previous machine-specific JDK 21 entry was removed from the wrapper properties. Gradle downloads the configured Node runtime; system-wide Node does not need to change.

During verification a checksum-verified JDK was placed under the ignored `build/upgrade/jdk/jdk-27+35` directory. This is a temporary verification installation, not a global JDK install; a root clean can remove it. Use a permanent JDK installation for normal development.

```powershell
.\gradlew.bat :backend:test :frontend:bootWar :auth_service:authCheck :auth_service:authBundle
```

Use the updated wrapper and committed Kotlin/JS lockfile together. `authCheck` compiles available checks; with the current source tree it has no auth test sources. It does not imply an auth unit-test suite ran.

## Existing Cassandra data

Changing the image does not validate or migrate an existing database. Do not start the new default image against retained data until you have a verified backup and have rehearsed the upgrade on a separate copy. The work in this upgrade tests a separate, disposable Cassandra 5 container; it does not replace the running development database or its volume.

The image and Compose build accept `CASSANDRA_VERSION`. To remain on the maintained 4.1 line while planning a retained-data upgrade, set `CASSANDRA_VERSION=4.1.12` in the Compose environment before rebuilding. Fresh deployments default to 5.0.9. This option does not make downgrading a volume previously used by 5.x safe.

For retained data, inventory the current version/schema, back up data and configuration, and rehearse the supported 4.1-to-5.0 procedure on a copy. Cassandra 5 has storage-compatibility settings and SSTable upgrade considerations; use the upstream release instructions rather than simply changing a version string. Preserve the old backup independently of any in-place conversion. Do not use `docker compose down --volumes` as an upgrade step.

## Verification performed

- Final Gradle command passed: `:backend:test :frontend:bootWar :auth_service:authCheck :auth_service:authBundle kotlinUpgradeYarnLock`. All seven backend tests passed. Auth had no test sources, so its check result establishes compilation, not unit-test coverage. The Node 26.12.1 runtime was downloaded and used by the final browser build.
- The wrapper JAR was regenerated; its SHA-256 matches Gradle's published `3b8a25775a69158b5ad2b1d17a80a88dc7b40a352a73eb27d06c612a7ce68e98` value.
- Built the Cassandra 5.0.9 image and applied both `project.cql` and `crm-schema.cql` to a disposable container with a separate anonymous volume. The running database reported version 5.0.9 and datacenter1. Cassandra retained its upstream materialized-view warning.
- Started the final WAR on Java 27 at a separate port, pointing only to that disposable database. Its login JSP returned 200, a synthetic nonexistent-user lookup returned the expected JSON/400 response, and headless Edge rendered its actual login form with jQuery 4 and no page errors.
- Started a separate copy of the Kobweb server on Java 27 with the upgraded API JAR and production browser bundle. Against a loopback-only mock backend, malformed login returned 400, successful fixture login created a session, and a company-list request returned 200/success. Headless Edge rendered the actual auth login form without page errors. The mock flow does not establish real-user or tenant authorization.
- A separate headless Edge integration fixture verified jQuery 4 event handling, DataTables 3 table initialization, DOMPurify sanitization, and Tailwind 4 custom colors/spacing using the pinned CDN URLs and integrity hashes.
- Verified published Java 27 JDK/JRE Resolute image tags; built the auth Dockerfile's Linux base/Chromium dependency layer; `docker compose config --quiet` passed. Full main/auth Docker release builds and Kobweb's complete export were not rerun. Host Gradle packaging and isolated runtime checks passed; memory headroom limited additional container verification.
- Temporary app/server processes and the labelled disposable Cassandra container/volume were stopped or removed. Existing Compose services and retained database data were not replaced.

Non-blocking warnings remain in upstream Scala/Zinc/driver native access and deprecated Gradle APIs, and Webpack reports the auth bundle above its recommended size budget (about 961 KiB). The build succeeds; these warnings do not constitute full production-readiness validation.

## References

- [Spring Boot requirements](https://docs.spring.io/spring-boot/system-requirements.html)
- [Spring Boot 4 migration guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-4.0-Migration-Guide)
- [Gradle releases](https://gradle.org/releases/)
- [Kotlin releases](https://kotlinlang.org/docs/releases.html)
- [Scala releases](https://www.scala-lang.org/download/all.html)
- [Kobweb 0.25.1](https://github.com/varabyte/kobweb/releases/tag/v0.25.1)
- [Cassandra releases](https://cassandra.apache.org/_/download.html)
- [Cassandra upgrade notes](https://github.com/apache/cassandra/blob/cassandra-5.0/NEWS.txt)
- [Adoptium container configuration](https://github.com/adoptium/containers/blob/main/config/temurin.yml)

The external Tailwind browser runtime remains development-oriented; this upgrade does not replace it with a production CSS build. Existing application authorization, invitation, and deployment-security work remains separate from dependency compatibility.
