package org.example.pages.users

import androidx.compose.runtime.Composable
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDialogElement
import org.w3c.dom.events.Event

/** Mount conditionally. The native dialog handles focus, Escape and the backdrop. */
@Composable
internal fun UserModal(title: String, onClose: () -> Unit, content: @Composable () -> Unit) {
    TagElement<HTMLDialogElement>("dialog", applyAttrs = {
        classes("border", "rounded-3", "shadow", "p-0", "bg-body", "text-body")
        attr("style", "width: min(720px, calc(100vw - 32px)); max-height: 90vh;")
        attr("aria-labelledby", "user-modal-title")
        ref { element ->
            element.showModal()
            val cancel: (Event) -> Unit = { it.preventDefault(); onClose() }
            element.addEventListener("cancel", cancel)
            onDispose { element.removeEventListener("cancel", cancel); element.close() }
        }
    }) {
        Div(attrs = { classes("p-3", "border-bottom", "d-flex", "justify-content-between", "align-items-center") }) {
            H2(attrs = { id("user-modal-title"); classes("h5", "mb-0") }) { Text(title) }
            Button(attrs = {
                classes("btn-close"); attr("type", "button"); attr("aria-label", "Close")
                onClick { onClose() }
            })
        }
        content()
    }
}