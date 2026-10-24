import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    val kobwebVersion: String = "0.25.1"
    kotlin("multiplatform") version "2.4.10"
    kotlin("plugin.compose") version "2.4.10"
    kotlin("plugin.serialization") version "2.4.10"
    id("com.varabyte.kobweb.application") version "$kobwebVersion"
}

group = "com.auth_service"
version = "1.0-SNAPSHOT"
val kobwebVersion: String = "0.25.1"

repositories {
    google()
    mavenCentral()
}

kotlin {
    configAsKobwebApplication(includeServer = true)

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
            implementation("com.varabyte.kobwebx:kobwebx-serialization-kotlinx:$kobwebVersion")
        }
        jvmMain.dependencies {
            implementation("com.varabyte.kobweb:kobweb-api:$kobwebVersion")
            implementation("com.fasterxml.jackson.core:jackson-databind:2.22.2")
        }
        jsMain.dependencies {
            implementation("androidx.compose.runtime:runtime:1.12.0")
            implementation("org.jetbrains.compose.html:html-core:1.11.1")
            implementation("com.varabyte.kobweb:kobweb-core:$kobwebVersion")
        }
        jvmTest.dependencies {
            implementation("org.junit.jupiter:junit-jupiter:5.12.2")
            runtimeOnly("org.junit.platform:junit-platform-launcher:1.12.2")
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

kobweb {
    app {
        index {
            description.set("Sign in to DJC CRM.")
            head.add {
                // DataTables test page: load core before its Bootstrap integration.
                link {
                    rel = "stylesheet"
                    href = "https://cdn.datatables.net/2.3.4/css/dataTables.bootstrap5.min.css"
                }
                script { src = "https://code.jquery.com/jquery-3.7.1.min.js" }
                script { src = "https://cdn.datatables.net/2.3.4/js/dataTables.min.js" }
                script { src = "https://cdn.datatables.net/2.3.4/js/dataTables.bootstrap5.min.js" }
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css"
                    attributes["integrity"] =
                        "sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB"
                    attributes["crossorigin"] = "anonymous"
                }
                script {
                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
                    attributes["integrity"] =
                        "sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
                    attributes["crossorigin"] = "anonymous"
                }
            }
        }
    }
}

tasks.register("authCheck") {
    group = "auth service"
    description = "Compile and run all available checks for the auth UI."
    dependsOn("check")
}

tasks.register("authBundle") {
    group = "auth service"
    description = "Create a production browser bundle for the auth UI."
    dependsOn("jsBrowserProductionWebpack")
}

tasks.register("authDev") {
    group = "auth service"
    description = "Start the auth UI development server."
    dependsOn("kobwebStart")
}
