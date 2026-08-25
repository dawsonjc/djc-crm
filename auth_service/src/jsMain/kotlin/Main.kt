package org.example

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.silk.SilkApp
import org.jetbrains.compose.web.dom.Style
import org.jetbrains.compose.web.dom.Text

@App
@Composable
fun App(content: @Composable () -> Unit) {
    SilkApp {
        Style { Text("@import url('/styles.css');") }
        content()
    }
}
