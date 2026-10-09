package com.auth_service.auth

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.UpdateHistoryMode
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.await
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Div
import org.w3c.fetch.Response

/** All pages are private except the exact login route. Never render private content before verification. */
@Composable
fun RequireLogin(content: @Composable () -> Unit) {
    val ctx: PageContext = rememberPageContext()
    val path: String = ctx.route.path
    val publicPage: Boolean = path.trimEnd('/') == "/login"
    var verified by remember(path) { mutableStateOf<Boolean>(false) }

    LaunchedEffect(path) {
        if (!publicPage) {
            val authenticated = try {
                val response: Response = window.fetch("/api/session").await()
                if (!response.ok) {
                    false
                } else {
                    val payload: Any? = response.json().await()
                    payload?.asDynamic()?.authenticated == true
                }
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                false
            }
            if (authenticated) verified = true
            else ctx.router.navigateTo("/login", updateHistoryMode = UpdateHistoryMode.REPLACE)
        }
    }

    val showContent: Boolean = publicPage || verified
    Div(attrs = { id("auth-route-content") }) {
        key(path, showContent) {
            if(showContent) {
                Div { content() }
            } else {
                Div(attrs = {
                    attr("role", "status")
                    attr("aria-live", "polite")
                }) {
                    Text("Checking your session…")
                }
            }
        }
    }
}
