package org.example

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp
import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import com.varabyte.kobweb.navigation.Router
import com.auth_service.auth.RequireLogin

private lateinit var appRouter: Router

@InitKobweb
fun initAuth(ctx: InitKobwebContext) {
    appRouter = ctx.router
}

@App
@Composable
fun App(@Suppress("UNUSED_PARAMETER") content: @Composable () -> Unit) {
    KobwebApp {
        // Place the guard inside the router's PageContext, before rendering any page.
        appRouter.renderActivePage { page -> RequireLogin(page) }
    }
}
