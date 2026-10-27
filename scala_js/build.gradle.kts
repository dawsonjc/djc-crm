import groovy.lang.Closure
import org.gradle.api.tasks.scala.ScalaCompile
import org.podval.tools.build.ScalaDependency
import org.podval.tools.scalajs.ScalaJSLinkTask

plugins {
    id("org.podval.tools.scalajs") version "2.3.0"
}

repositories {
    mavenCentral()
}

val scalaVersion = "3.10.0"
val scalaJsVersion = "1.22.0"

scala.scalaVersion.set(scalaVersion)

// Podval's dependency configuration API accepts Groovy closures, including in Kotlin DSL.
fun configureDependency(
    configure: (ScalaDependency) -> ScalaDependency
): Closure<ScalaDependency> = object : Closure<ScalaDependency>(null) {
    @Suppress("unused")
    fun doCall(dependency: ScalaDependency): ScalaDependency = configure(dependency)
}

dependencies {
    // Sets scalaBackend.backendVersion.
    implementation(scalaBackend.scalaDependency(
        "org.scala-js", "scalajs-library", scalaJsVersion,
        configureDependency { it.scala2().jvm() }
    ))
    implementation(scalaBackend.scalaDependency("org.scala-js", "scalajs-dom", "2.8.1"))
    if (scalaBackend.isScala3) {
        implementation(scalaBackend.scalaDependency(
            "org.scala-lang", "scala3-library", scalaBackend.scalaVersion,
            configureDependency { it.scala3() }
        ))
    }
    add("scalajs", scalaBackend.pluginDependency(
        "org.scala-js", "scalajs-linker", scalaBackend.backendVersion,
        configureDependency { it.scala2() }
    ))
    add("scalajs", scalaBackend.pluginDependency(
        "org.scala-js", "scalajs-sbt-test-adapter", scalaBackend.backendVersion,
        configureDependency { it.scala2() }
    ))
    add("scalajs", scalaBackend.pluginDependency(
        "org.scala-js", "scalajs-env-jsdom-nodejs", "1.1.1",
        configureDependency { it.scala2() }
    ))
    if (!scalaBackend.isScala3) {
        add("scalaCompilerPlugins", scalaBackend.scalaDependency(
            "org.scala-js", "scalajs-compiler", scalaBackend.backendVersion,
            configureDependency { it.scala2().scalaCompilerPlugin() }
        ))
    }
    if (!scalaBackend.isScala3 && scalaBackend.nonJvmJUnit4present) {
        add("testScalaCompilerPlugins", scalaBackend.scalaDependency(
            "org.scala-js", "scalajs-junit-test-plugin", scalaBackend.backendVersion,
            configureDependency { it.scala2().scalaCompilerPlugin() }
        ))
    }
    testRuntimeOnly(scalaBackend.scalaDependency(
        "org.scala-js", "scalajs-test-bridge", scalaBackend.backendVersion,
        configureDependency { it.scala2().jvm() }
    ))

    implementation("io.udash:udash-jquery_sjs1_2.13:3.4.0")
}

tasks.withType<ScalaCompile>().configureEach {
    scalaCompileOptions.additionalParameters = listOf("-scalajs")
}

val link = tasks.named<ScalaJSLinkTask.Main>("link") {
    optimization.set("Full")
    moduleKind.set("ESModule")
    moduleSplitStyle.set("SmallModulesFor")
    esVersion.set("2021")
    smallModulesFor.set(listOf("com.brewery.web.frontend"))
    prettyPrint.set(false)
    useWebAssembly.set(false)

    mapOf(
        "shell" to "ShellMain",
        "login" to "LoginMain",
        "register" to "RegistrationMain",
        "conversations" to "ConversationsMain",
        "admin" to "AdminMain"
    ).forEach { (moduleId, entryClass) ->
        moduleInitializers.create(moduleId) {
            className.set("com.brewery.web.frontend.entry.$entryClass")
            mainMethodName.set("main")
            mainMethodHasArgs.set(false)
        }
    }

    doLast {
        project.sync {
            from(getJSDirectory())
            into(rootProject.file("frontend/src/main/webapp/static/js"))
        }
        println("Linked")
    }
}

tasks.named("assemble") {
    dependsOn(link)
}
