package org.example.pages.users

import androidx.compose.runtime.Composable
import com.auth_service.model.CompanyUser
import org.jetbrains.compose.web.dom.*

@Composable
fun ViewUserModal(user: CompanyUser, onClose: () -> Unit) {
    UserModal("User details", onClose) {
        Div(attrs = { classes("p-3", "row", "g-3") }) {
            UserDetail("User ID", user.userId?.toString())
            UserDetail("Company", user.companyName)
            UserDetail("Company ID", user.companyId?.toString())
            UserDetail("Username", user.username)
            UserDetail("Email", user.email)
            UserDetail("First name", user.firstName)
            UserDetail("Last name", user.lastName)
            UserDetail("Full name", user.fullName)
            UserDetail("Status", user.status)
            UserDetail("Account verification", user.accountVerificationStatus)
            UserDetail("Language preference", user.languagePreference)
            UserDetail("Time zone", user.timezone)
            UserDetail("Two-factor authentication", user.twoFactorAuthentication?.let { if (it) "Enabled" else "Disabled" })
            UserDetail("Roles", user.roles?.mapNotNull { it.roleName }?.joinToString(", "))
            UserDetail("Created", user.createDate?.toString())
            UserDetail("Updated", user.updateDate?.toString())
            UserDetail("Last login", user.lastLoginDate?.toString())
        }
        Div(attrs = { classes("p-3", "border-top", "text-end") }) {
            Button(attrs = { classes("btn", "btn-secondary"); attr("type", "button"); onClick { onClose() } }) { Text("Close") }
        }
    }
}

@Composable
private fun UserDetail(label: String, value: String?) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Div(attrs = { classes("small", "text-body-secondary") }) { Text(label) }
        Div(attrs = { classes("text-break") }) { Text(value?.takeIf(String::isNotBlank) ?: "—") }
    }
}