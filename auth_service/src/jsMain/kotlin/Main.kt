package org.example

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.KobwebApp

@App
@Composable
fun App(content: @Composable () -> Unit) {
    KobwebApp {
        content()
    }
}

fun index() {
    
}
