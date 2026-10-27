import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin

plugins {
    val kobwebVersion: String = "0.25.1"
    kotlin("multiplatform") version "2.4.21"
    kotlin("plugin.compose") version "2.4.21"
    kotlin("plugin.serialization") version "2.4.21"
    id("com.varabyte.kobweb.application") version "$kobwebVersion"
}

group = "com.auth_service"
version = "1.0-SNAPSHOT"
val kobwebVersion: String = "0.25.1"

repositories {
    google()
    mavenCentral()
}

rootProject.allprojects {
    plugins.withType<NodeJsPlugin> {
        extensions.configure<NodeJsEnvSpec> {
            version = "26.12.1"
        }
    }
}

kotlin {
    jvmToolchain(27)
    configAsKobwebApplication(includeServer = true)

    // Kotlin 2.4 supports JVM 26 bytecode; its compiler and server run on JDK 27.
    jvm {
        compilerOptions.jvmTarget.set(JvmTarget.JVM_26)
    }

    js {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.12.0")
            implementation("com.varabyte.kobwebx:kobwebx-serialization-kotlinx:$kobwebVersion")
        }
        jvmMain.dependencies {
            implementation("com.bucket4j:bucket4j_jdk17-core:8.21.0")
            implementation("com.github.ben-manes.caffeine:caffeine:3.3.1")
            implementation("com.varabyte.kobweb:kobweb-api:$kobwebVersion")
            implementation("tools.jackson.core:jackson-databind:3.2.4")
        }
        jsMain.dependencies {
            implementation("androidx.compose.runtime:runtime:1.12.1")
            implementation("org.jetbrains.compose.html:html-core:1.12.2")
            implementation("com.varabyte.kobweb:kobweb-core:$kobwebVersion")
        }
        jvmTest.dependencies {
            implementation("org.junit.jupiter:junit-jupiter:6.1.3")
            runtimeOnly("org.junit.platform:junit-platform-launcher:6.1.3")
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(26)
}

kobweb {
    app {
        index {
            description.set("Sign in to DJC CRM.")
            head.add {
                // DataTables test page: load core before its Bootstrap integration.
                link {
                    rel = "stylesheet"
                    href = "https://cdn.jsdelivr.net/npm/datatables.net-bs5@3.1.4/css/dataTables.bootstrap5.min.css"
                    attributes["integrity"] = "sha384-OZKa6QSlaaq/LGR1sBFkYhC0c/nacIFh1chsblhDUxggC9Zb0XLEEMs95i1Kydnt"
                    attributes["crossorigin"] = "anonymous"
                }
                script {
                    src = "https://code.jquery.com/jquery-4.0.0.min.js"
                    attributes["integrity"] = "sha384-fgGyf7Mo7DURSOMnOy7ed+dkq5Job205Gnzu6QIg0BOHKaqt4D76Dt8VlDCzcMHV"
                    attributes["crossorigin"] = "anonymous"
                }
                script {
                    src = "https://cdn.jsdelivr.net/npm/datatables.net@3.1.4/js/dataTables.min.js"
                    attributes["integrity"] = "sha384-Lg8qwUDhp1uuDJinT3SMR9sxQyVjC/2NPPFkbPahEVgqBRrWGI6zfmrgPYTaVgrS"
                    attributes["crossorigin"] = "anonymous"
                }
                script {
                    src = "https://cdn.jsdelivr.net/npm/datatables.net-bs5@3.1.4/js/dataTables.bootstrap5.min.js"
                    attributes["integrity"] = "sha384-4d8X9sr6Gnv9AgIQn6bv3lmQxj5fD+9bVAun0/XMmdy7oPRvT0adfiUUiiYpi4Ck"
                    attributes["crossorigin"] = "anonymous"
                }
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
