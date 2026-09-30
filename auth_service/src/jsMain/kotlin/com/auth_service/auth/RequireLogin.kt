package com.auth_service.auth

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.UpdateHistoryMode
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.await
import org.jetbrains.compose.web.dom.Text
import org.w3c.fetch.Response

/** All pages are private except the exact login route. Never render private content before verification. */
@Composable
fun RequireLogin(content: @Composable () -> Unit) {
    val ctx = rememberPageContext()
    val path = ctx.route.path
    val publicPage = path.trimEnd('/') == "/login"
    var verified by remember(path) { mutableStateOf(false) }

    LaunchedEffect(path) {
        if (!publicPage) {
            val authenticated = try {
                val response: Response = window.fetch("/api/session").await()
                response.ok && response.json().await().asDynamic().authenticated
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                false
            }
            if (authenticated) verified = true
            else ctx.router.navigateTo("/login", updateHistoryMode = UpdateHistoryMode.REPLACE)
        }
    }

    if (publicPage || verified) content() else Text("Checking your session…")
}
