package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.required
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import org.w3c.fetch.RequestInit
import kotlin.js.JSON
import kotlin.js.json

private const val LOGIN_ENDPOINT: String = "/account/login"

@Page(routeOverride = "/login")
@Composable
fun LoginPage() {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf<String>(value = "") }
    var password by remember { mutableStateOf<String>(value = "") }
    var errorMessage by remember { mutableStateOf<String?>(value = null) }
    var isSubmitting by remember { mutableStateOf<Boolean>(value = false) }

    fun submitLogin() {
        if(isSubmitting || email.isBlank() || password.isBlank()) {
            return
        }

        scope.launch {
            isSubmitting = true
            errorMessage = null
            try {
                val response = window.fetch(
                    LOGIN_ENDPOINT,
                    RequestInit(
                        method = "POST",
                        headers = json("Content-Type" to "application/json"),
                        body = JSON.stringify(json(
                            "username" to email.trim(),
                            "password" to password,
                        )),
                    ),
                ).await()
                val body = response.json().await().asDynamic()

                if (response.ok && body.success == true) {
                    window.location.assign("/")
                } else {
                    errorMessage = body.message?.toString()
                        ?.takeIf { it.isNotBlank() }
                        ?: "Invalid email or password."
                }
            } catch (_: Throwable) {
                errorMessage = "Unable to sign in. Check that the backend is running and try again."
            } finally {
                isSubmitting = false
            }
        }
    }

    Div(attrs = {
        classes(
            "container-fluid",
            "min-vh-100",
            "bg-dark",
            "d-flex",
            "align-items-center",
            "justify-content-center",
            "py-5",
        )
    }) {
        Div(attrs = {
            classes("card", "border-0", "shadow-lg", "w-100")
            style { maxWidth(28.cssRem) }
        }) {
            Div(attrs = { classes("card-body", "p-4", "p-md-5") }) {
                H1(attrs = { classes("card-title", "h2", "text-center", "mb-2") }) {
                    Text("Sign in")
                }
                P(attrs = { classes("text-secondary", "text-center", "mb-4") }) {
                    Text("Welcome back. Enter your account details to continue.")
                }

                errorMessage?.let { message ->
                    Div(attrs = {
                        classes("alert", "alert-danger")
                        attr("role", "alert")
                    }) {
                        Text(message)
                    }
                }

                Form {
                    Div(attrs = { classes("mb-3") }) {
                        Label(forId = "email", attrs = { classes("form-label") }) {
                            Text("Email")
                        }
                        Input(type = InputType.Email, attrs = {
                            id("email")
                            classes("form-control")
                            value(email)
                            attr("name", "email")
                            attr("placeholder", "you@example.com")
                            attr("autocomplete", "email")
                            required()
                            if (isSubmitting) disabled()
                            onInput { elem -> email = elem.value }
                            onKeyUp { elem ->
                                if(elem.key == "Enter") {
                                    submitLogin()
                                }
                            }
                        })
                    }

                    Div(attrs = { classes("mb-3") }) {
                        Label(forId = "password", attrs = { classes("form-label") }) {
                            Text("Password")
                        }
                        Input(type = InputType.Password, attrs = {
                            id("password")
                            classes("form-control")
                            value(password)
                            attr("name", "password")
                            attr("placeholder", "Enter your password")
                            attr("autocomplete", "current-password")
                            required()
                            if (isSubmitting) disabled()
                            onInput { elem -> password = elem.value }
                            onKeyUp { elem ->
                                if (elem.key == "Enter") {
                                    submitLogin()
                                }
                            }
                        })
                    }

                    Button(attrs = {
                        classes("btn", "btn-primary", "w-100")
                        type(ButtonType.Button)
                        if (isSubmitting) disabled()
                        onClick { submitLogin() }
                    }) {
                        if (isSubmitting) {
                            Span(attrs = {
                                classes("spinner-border", "spinner-border-sm", "me-2")
                                attr("role", "status")
                                attr("aria-hidden", "true")
                            })
                        }
                        Text(if (isSubmitting) "Signing in…" else "Sign in")
                    }
                }
            }
        }
    }
}
