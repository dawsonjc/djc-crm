package org.example.pages.users

import androidx.compose.runtime.Composable
import com.auth_service.model.CompanyUser
import org.jetbrains.compose.web.dom.*

@Composable
fun DeleteUserModal(user: CompanyUser, saving: Boolean, error: String?, onClose: () -> Unit, onConfirm: () -> Unit) {
    UserModal("Delete user", onClose = { if (!saving) onClose() }) {
        Div(attrs = { classes("p-3") }) {
            P { Text("Permanently delete ${user.fullName ?: user.username ?: user.email ?: user.userId} and their account? This cannot be undone.") }
            error?.let { Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(it) } }
        }
        Div(attrs = { classes("p-3", "border-top", "d-flex", "justify-content-end", "gap-2") }) {
            Button(attrs = {
                classes("btn", "btn-outline-secondary"); attr("type", "button")
                if (saving) attr("disabled", "")
                onClick { onClose() }
            }) { Text("Cancel") }
            Button(attrs = {
                classes("btn", "btn-danger"); attr("type", "button")
                if (saving) attr("disabled", "")
                onClick { onConfirm() }
            }) { Text(if (saving) "Deleting…" else "Delete user") }
        }
    }
}