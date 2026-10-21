package org.example.pages.company

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.model.CompanyUser
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import com.auth_service.serialization.AppJson
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
import org.example.pages.users.AddUserModal
import org.example.pages.users.EditUserModal
import org.example.pages.users.ViewUserModal

@Page(routeOverride = "/company/id/{id}")
@Composable
fun CompanyView(context: PageContext) {
    val id = context.route.params["id"]
    var company by remember(id) { mutableStateOf<Company?>(null) }
    var loading by remember(id) { mutableStateOf(true) }
    var error by remember(id) { mutableStateOf<String?>(null) }

    LaunchedEffect(id) {
        try {
            val response = window.api.get("/company/get/$id")
            check(response.ok) { "Could not load company details (HTTP ${response.status})." }
            val body = AppJson.parseToJsonElement(response.bodyAsBytes().decodeToString()).jsonObject
            company = AppJson.decodeFromJsonElement<Company>(
                body["data"] ?: error("Company response is missing data.")
            )
            if (company == null) error = "Company not found."
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            error = e.message ?: "Could not load company details."
        } finally {
            loading = false
        }
    }

    Div(attrs = { classes("container", "mx-auto", "py-4", "px-3"); attr("style", "max-width: 1080px;") }) {
        A(href = "/company", attrs = { classes("small", "text-decoration-none") }) { Text("← Companies") }
        Div(attrs = { classes("my-3") }) {
            H1(attrs = { classes("h3", "mb-1") }) { Text(company?.name ?: "Company details") }
            P(attrs = { classes("small", "text-body-secondary", "text-break", "mb-0") }) { Text("Company ID: ${id ?: "Unavailable"}") }
        }
        when {
            loading -> P(attrs = { attr("role", "status") }) { Text("Loading company details…") }
            error != null -> Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(error!!) }
            else -> company?.let { CompanyDetails(it) }
        }
    }
}

@Composable
private fun CompanyDetails(company: Company) {
    // Local preview state only. Replace with your CompanyUsers API when it is ready.
    var users by remember(company.companyId) { mutableStateOf(company.companyUsers) }
    var dialog by remember { mutableStateOf<String?>(null) }
    var selectedUser by remember { mutableStateOf<CompanyUser?>(null) }
    var selectedIndex by remember { mutableStateOf(-1) }
    var changed by remember { mutableStateOf(false) }

    Div(attrs = { classes("card", "shadow-sm", "mb-4") }) {
        Div(attrs = { classes("card-header", "bg-body", "fw-semibold", "py-3") }) { Text("Company information") }
        Div(attrs = { classes("card-body", "row", "g-3") }) {
            Detail("Legal name", company.legalName)
            Detail("Status", company.status)
            Detail("Billing email", company.billingEmail)
            Detail("Phone", company.phone)
            Detail("Website", company.website)
            Detail("Primary administrator", company.primaryAdminUserId?.toString())
            Detail("Billing address", company.billingAddress?.let {
                listOfNotNull(it.line1, it.line2, it.city, it.region, it.postalCode, it.countryCode)
                    .filter(String::isNotBlank).joinToString(", ")
            })
            Detail("Created", company.createdAt?.toString())
            Detail("Last updated", company.updatedAt?.toString())
        }
    }
    Div(attrs = { classes("card", "shadow-sm") }) {
        Div(attrs = { classes("card-header", "bg-body", "d-flex", "justify-content-between", "align-items-center", "py-3") }) {
            H2(attrs = { classes("h5", "mb-0") }) { Text("Company users (${users.size})") }
            Button(attrs = {
                classes("btn", "btn-primary", "btn-sm")
                attr("type", "button")
                onClick { selectedUser = null; dialog = "Add" }
            }) { Text("+ Add user") }
        }
        Div(attrs = { classes("card-body") }) {
            P(attrs = { classes("small", "text-body-secondary"); attr("role", "status") }) {
                Text(if (changed) "Unsaved local changes — refreshing or leaving this page discards them."
                    else "Preview only. Company users are not loaded from an API yet; additions and edits stay on this page.")
            }
            Div(attrs = { classes("table-responsive") }) {
                Table(attrs = { classes("table", "table-sm", "table-hover", "align-middle", "mb-0") }) {
                    Thead(attrs = { classes("table-light") }) {
                        Tr { listOf("Name / Username", "Email", "Status", "Verification", "Last login", "Actions").forEach { label ->
                            Th(attrs = { attr("scope", "col") }) { Text(label) }
                        } }
                    }
                    Tbody {
                        if (users.isEmpty()) Tr {
                            Td(attrs = { colspan(6); classes("text-center", "text-body-secondary", "py-4") }) {
                                Text("No users loaded. Add a user to preview the table.")
                            }
                        }
                        users.forEachIndexed { index, user ->
                            Tr {
                                Td(attrs = { classes("py-3") }) {
                                    Div(attrs = { classes("fw-semibold") }) {
                                        Text(user.fullName?.takeIf(String::isNotBlank)
                                            ?: listOfNotNull(user.firstName, user.lastName).joinToString(" ").ifBlank { user.username ?: "Unnamed user" })
                                    }
                                    Div(attrs = { classes("small", "text-body-secondary") }) { Text(user.username ?: "—") }
                                }
                                Td(attrs = { classes("text-break") }) { Text(user.email ?: "—") }
                                Td { Span(attrs = { classes("badge", if (user.status.equals("Active", true)) "text-bg-success" else "text-bg-secondary") }) { Text(user.status ?: "—") } }
                                Td { Text(user.accountVerificationStatus ?: "—") }
                                Td(attrs = { classes("small") }) { Text(user.lastLoginDate?.toString() ?: "—") }
                                Td(attrs = { classes("text-nowrap") }) {
                                    listOf("Edit", "View").forEach { action ->
                                        Button(attrs = {
                                            classes("btn", "btn-sm", "me-2", if (action == "Edit") "btn-outline-primary" else "btn-outline-secondary")
                                            attr("type", "button")
                                            onClick { selectedUser = user; selectedIndex = index; dialog = action }
                                        }) { Text(action) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // Hook the submit callbacks to your APIs when ready. Until then only this local list changes.
    when (dialog) {
        "Add" -> AddUserModal(company = company, onClose = { dialog = null }, onSubmit = { draft ->
            users = if (draft.companyId == company.companyId) users + draft else users
            changed = true
            dialog = null
        })
        "Edit" -> selectedUser?.let { user ->
            EditUserModal(user, company = company, onClose = { dialog = null }, onSubmit = { draft ->
                users = users.mapIndexed { index, existing -> if (index == selectedIndex) draft else existing }
                    .filterIndexed { index, existing -> index != selectedIndex || existing.companyId == company.companyId }
                changed = true
                dialog = null
            })
        }
        "View" -> selectedUser?.let { ViewUserModal(it, onClose = { dialog = null }) }
    }
}
@Composable
private fun Detail(label: String, value: String?) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Div(attrs = { classes("small", "text-body-secondary", "mb-1") }) { Text(label) }
        Div(attrs = { classes("text-break") }) { Text(value?.takeIf(String::isNotBlank) ?: "—") }
    }
}
