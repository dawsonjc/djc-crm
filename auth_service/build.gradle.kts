import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    kotlin("multiplatform") version "2.4.0"
    kotlin("plugin.compose") version "2.4.0"
    id("com.varabyte.kobweb.application") version "0.25.0"
}

group = "com.example"
version = "1.0-SNAPSHOT"

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
        jvmMain.dependencies {
            implementation("com.varabyte.kobweb:kobweb-api:0.25.0")
        }
        jsMain.dependencies {
            implementation("androidx.compose.runtime:runtime:1.11.2")
            implementation("org.jetbrains.compose.html:html-core:1.11.1")
            implementation("com.varabyte.kobweb:kobweb-core:0.25.0")
        }
    }
}

kobweb {
    app {
        index {
            description.set("Sign in to DJC CRM.")
            head.add {
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
