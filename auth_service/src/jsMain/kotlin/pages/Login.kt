package pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.Label
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
import org.w3c.fetch.RequestInit
import kotlin.js.JSON
import kotlin.js.json

private const val LOGIN_ENDPOINT = "/account/login"

@Page(routeOverride = "/login")
@Composable
fun LoginPage() {
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isSubmitting by remember { mutableStateOf(false) }

    fun submitLogin() {
        if (isSubmitting || email.isBlank() || password.isBlank()) return

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

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .minHeight(100.vh)
            .backgroundColor(Colors.Black),
        colorModeOverride = ColorMode.DARK,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .minHeight(100.vh)
                .padding(1.cssRem),
            contentAlignment = Alignment.Center,
        ) {
            Surface(
                modifier = Modifier
                    .width(100.percent)
                    .maxWidth(28.cssRem)
                    .padding(2.cssRem)
                    .borderRadius(1.cssRem)
                    .boxShadow(blurRadius = 3.cssRem, color = Colors.Black),
                colorModeOverride = ColorMode.LIGHT,
            ) {
                Column(Modifier.fillMaxWidth()) {
                    H1(attrs = Modifier
                        .fillMaxWidth()
                        .margin(0.px)
                        .fontSize(1.875.cssRem)
                        .fontWeight(FontWeight.Bold)
                        .toAttrs()
                    ) { Text("Sign in") }

                    P(attrs = Modifier
                        .fillMaxWidth()
                        .margin(top = 0.5.cssRem, bottom = 1.5.cssRem)
                        .toAttrs()
                    ) { Text("Welcome back. Enter your account details to continue.") }

                    errorMessage?.let { message ->
                        Box(Modifier
                            .fillMaxWidth()
                            .margin(bottom = 1.cssRem)
                            .padding(0.75.cssRem)
                            .borderRadius(0.5.cssRem)
                            .backgroundColor(Colors.LightPink)
                        ) { Text(message) }
                    }

                    Label(
                        target = ElementTarget.NextSibling,
                        label = "Email",
                        modifier = Modifier.fillMaxWidth().margin(bottom = 0.375.cssRem),
                    )
                    Input(
                        type = InputType.Email,
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth().margin(bottom = 1.cssRem),
                        placeholder = "you@example.com",
                        enabled = !isSubmitting,
                        required = true,
                        onCommit = { submitLogin() },
                    )

                    Label(
                        target = ElementTarget.NextSibling,
                        label = "Password",
                        modifier = Modifier.fillMaxWidth().margin(bottom = 0.375.cssRem),
                    )
                    TextInput(
                        text = password,
                        onTextChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = "Enter your password",
                        password = true,
                        enabled = !isSubmitting,
                        required = true,
                        onCommit = { submitLogin() },
                    )

                    Button(
                        onClick = { submitLogin() },
                        modifier = Modifier.fillMaxWidth().margin(top = 1.25.cssRem),
                        enabled = !isSubmitting,
                    ) {
                        Text(if (isSubmitting) "Signing in…" else "Sign in")
                    }
                }
            }
        }
    }
}
