package org.example.pages.users

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.model.CompanyUser
import com.auth_service.model.Languages
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*

/** Profile fields only; submit is supplied by the caller. No network requests here. */
@Composable
fun UserForm(user: CompanyUser?, defaultCompany: Company, onCancel: () -> Unit, onSubmit: (CompanyUser) -> Unit) {
    var draft by remember(user, defaultCompany.companyId) { mutableStateOf((user ?: CompanyUser(status = "Active", accountVerificationStatus = "Pending")).let {
        it.copy(
            companyId = it.companyId ?: defaultCompany.companyId,
            companyName = if (it.companyId == null || it.companyId == defaultCompany.companyId) it.companyName ?: defaultCompany.name else it.companyName,
            languagePreference = it.languagePreference?.takeIf(String::isNotBlank) ?: "en",
            timezone = it.timezone?.takeIf(String::isNotBlank) ?: "UTC",
        )
    }) }
    var error by remember(user) { mutableStateOf<String?>(null) }
    Form(attrs = {
        onSubmit { event ->
            event.preventDefault()
            if (draft.companyId == null) {
                error = "Select a company for this user."
            } else if (draft.username.isNullOrBlank() || draft.email.isNullOrBlank()) {
                error = "Username and email are required."
            } else {
                onSubmit(draft.copy(username = draft.username?.trim(), email = draft.email?.trim()))
            }
        }
    }) {
        Div(attrs = { classes("p-3") }) {
            error?.let { Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(it) } }
            if (user != null) P(attrs = { classes("small", "text-body-secondary", "text-break") }) {
                Text("User ID: ${user.userId ?: "Not assigned"}")
            }
            Div(attrs = { classes("row", "g-3") }) {
                UserCompanySelect(
                    companyId = draft.companyId,
                    companyName = draft.companyName,
                    defaultCompany = defaultCompany,
                ) { selected -> draft = draft.copy(companyId = selected.companyId, companyName = selected.name) }
                UserField("username", "Username", draft.username, required = true) { draft = draft.copy(username = it) }
                UserField("email", "Email", draft.email, type = InputType.Email, required = true) { draft = draft.copy(email = it) }
                UserField("first-name", "First name", draft.firstName) { draft = draft.copy(firstName = it) }
                UserField("last-name", "Last name", draft.lastName) { draft = draft.copy(lastName = it) }
                UserField("full-name", "Full name", draft.fullName) { draft = draft.copy(fullName = it) }
                SearchableUserSelect(
                    id = "user-language", label = "Language preference", value = draft.languagePreference ?: "en",
                    options = Languages.options.map { it.tag to "${it.label} (${it.tag})" },
                ) { draft = draft.copy(languagePreference = it) }
                SearchableUserSelect(
                    id = "user-timezone", label = "Time zone", value = draft.timezone ?: "UTC",
                    options = remember { userTimeZones().map { it to it.replace('_', ' ') } },
                ) { draft = draft.copy(timezone = it) }
                UserChoice("status", "Status", draft.status, listOf("Active", "Disabled")) { draft = draft.copy(status = it) }
                UserChoice("verification", "Account verification", draft.accountVerificationStatus, listOf("Pending", "Verified", "Blocked")) {
                    draft = draft.copy(accountVerificationStatus = it)
                }
                // Authentication settings, credentials, roles and audit fields are preserved by copy().
            }
            P(attrs = { classes("small", "text-body-secondary", "mt-3", "mb-0") }) {
                Text("Local draft only. Changes are not sent to the server.")
            }
        }
        Div(attrs = { classes("p-3", "border-top", "d-flex", "justify-content-end", "gap-2") }) {
            Button(attrs = { classes("btn", "btn-outline-secondary"); attr("type", "button"); onClick { onCancel() } }) { Text("Cancel") }
            Button(attrs = { classes("btn", "btn-primary"); attr("type", "submit") }) { Text("Save local draft") }
        }
    }
}

@Composable
private fun UserField(
    id: String, label: String, value: String?, type: InputType<String> = InputType.Text,
    required: Boolean = false, onChange: (String?) -> Unit,
) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Label(forId = "user-$id", attrs = { classes("form-label") }) { Text(label) }
        Input(type, attrs = {
            id("user-$id"); classes("form-control"); value(value.orEmpty())
            if (required) attr("required", "")
            onInput { onChange(it.value.takeIf(String::isNotBlank)) }
        })
    }
}

@Composable
private fun UserChoice(id: String, label: String, value: String?, options: List<String>, onChange: (String?) -> Unit) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Label(forId = "user-$id", attrs = { classes("form-label") }) { Text(label) }
        Select(attrs = { id("user-$id"); classes("form-select"); onChange { onChange(it.value?.takeIf(String::isNotBlank)) } }) {
            Option("", attrs = { if (value == null) selected() }) { Text("Not specified") }
            // Preserve any existing backend spelling, including uppercase enum names.
            (options + listOfNotNull(value)).distinct().forEach { option ->
                Option(option, attrs = { if (option == value) selected() }) { Text(option) }
            }
        }
    }
}