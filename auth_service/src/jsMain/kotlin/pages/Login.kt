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
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.Label
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.fetch.RequestInit
import kotlin.js.JSON
import kotlin.js.json

private const val LOGIN_ENDPOINT = "/account/login"

@Page("/login")
@Composable
fun LoginPage() {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }
    val submitLogin = {
        if (!isSubmitting && email.isNotBlank() && password.isNotBlank()) {
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
    }

    Div(attrs = { classes("login-page") }) {
        Div(attrs = { classes("login-card") }) {
            H1 { Text("Sign in") }
            P(attrs = { classes("login-intro") }) {
                Text("Welcome back. Enter your account details to continue.")
            }

            errorMessage?.let { message ->
                Div(attrs = {
                    classes("login-error")
                    attr("role", "alert")
                }) { Text(message) }
            }

            Form(attrs = { classes("login-form") }) {
                Label(forId = "email") { Text("Email") }
                Input(type = InputType.Email, attrs = {
                    id("email")
                    value(email)
                    attr("autocomplete", "email")
                    required()
                    if (isSubmitting) disabled()
                    onInput { email = it.value }
                })

                Label(forId = "password") { Text("Password") }
                Input(type = InputType.Password, attrs = {
                    id("password")
                    value(password)
                    attr("autocomplete", "current-password")
                    required()
                    if (isSubmitting) disabled()
                    onInput { password = it.value }
                })

                Button(attrs = {
                    type(ButtonType.Button)
                    if (isSubmitting) disabled()
                    onClick { submitLogin() }
                }) { Text(if (isSubmitting) "Signing in…" else "Sign in") }
            }
        }
    }
}
