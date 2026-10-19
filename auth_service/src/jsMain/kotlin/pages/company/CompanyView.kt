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
import kotlin.uuid.Uuid

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
                        Tr { listOf("User ID", "Role", "Status", "Actions").forEach { label ->
                            Th(attrs = { attr("scope", "col") }) { Text(label) }
                        } }
                    }
                    Tbody {
                        if (users.isEmpty()) Tr {
                            Td(attrs = { colspan(4); classes("text-center", "text-body-secondary", "py-4") }) {
                                Text("No users loaded. Add a user to preview the table.")
                            }
                        }
                        users.forEach { user ->
                            Tr {
                                Td(attrs = { classes("small", "text-break", "py-3") }) { Text(user.userId.toString()) }
                                Td { Text(user.role) }
                                Td { Span(attrs = { classes("badge", if (user.status == "Active") "text-bg-success" else "text-bg-secondary") }) { Text(user.status) } }
                                Td(attrs = { classes("text-nowrap") }) {
                                    listOf("Edit", "View").forEach { action ->
                                        Button(attrs = {
                                            classes("btn", "btn-sm", "me-2", if (action == "Edit") "btn-outline-primary" else "btn-outline-secondary")
                                            attr("type", "button")
                                            onClick { selectedUser = user; dialog = action }
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
    dialog?.let { mode ->
        key(mode, selectedUser) {
            CompanyUserDialog(mode, company, selectedUser, onClose = { dialog = null }, onSave = { draft ->
                users = if (mode == "Add") users + draft else users.map { if (it.userId == draft.userId) draft else it }
                changed = true
                dialog = null
            }, existingIds = users.map { it.userId })
        }
    }
}

@Composable
private fun Detail(label: String, value: String?) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Div(attrs = { classes("small", "text-body-secondary", "mb-1") }) { Text(label) }
        Div(attrs = { classes("text-break") }) { Text(value?.takeIf(String::isNotBlank) ?: "—") }
    }
}

@Composable
private fun CompanyUserDialog(
    mode: String, company: Company, user: CompanyUser?, onClose: () -> Unit,
    onSave: (CompanyUser) -> Unit, existingIds: List<Uuid>,
) {
    var userId by remember { mutableStateOf(user?.userId?.toString().orEmpty()) }
    var role by remember { mutableStateOf(user?.role.orEmpty()) }
    var status by remember { mutableStateOf(user?.status ?: "Active") }
    var error by remember { mutableStateOf<String?>(null) }
    val readOnly = mode == "View"

    // Native dialog provides modal focus handling, Escape, and focus restoration.
    TagElement<org.w3c.dom.HTMLDialogElement>("dialog", applyAttrs = {
        classes("border", "rounded-3", "shadow", "p-0")
        attr("style", "width: min(560px, calc(100vw - 32px));")
        attr("aria-labelledby", "company-user-dialog-title")
        ref { element ->
            element.showModal()
            val cancel: (org.w3c.dom.events.Event) -> Unit = { it.preventDefault(); onClose() }
            element.addEventListener("cancel", cancel)
            onDispose { element.removeEventListener("cancel", cancel); element.close() }
        }
    }) {
        Form(attrs = {
            onSubmit { event ->
                event.preventDefault()
                val parsed = runCatching { Uuid.parse(userId.trim()) }.getOrNull()
                when {
                    parsed == null -> error = "Enter a valid user UUID."
                    role.isBlank() -> error = "Enter a role."
                    mode == "Add" && parsed in existingIds -> error = "This user is already in the table."
                    company.companyId == null -> error = "A company ID is required."
                    else -> onSave(CompanyUser(company.companyId, parsed, role.trim(), status, user?.createdAt, user?.updatedAt))
                }
            }
        }) {
            Div(attrs = { classes("p-3", "border-bottom", "d-flex", "justify-content-between", "align-items-center") }) {
                H2(attrs = { id("company-user-dialog-title"); classes("h5", "mb-0") }) { Text("$mode company user") }
                Button(attrs = { classes("btn-close"); attr("type", "button"); attr("aria-label", "Close"); onClick { onClose() } })
            }
            Div(attrs = { classes("p-3") }) {
                error?.let { Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(it) } }
                if (readOnly) {
                    Detail("User ID", userId)
                    Detail("Company ID", company.companyId?.toString())
                    Detail("Role", role)
                    Detail("Status", status)
                    Detail("Created", user?.createdAt?.toString())
                    Detail("Last updated", user?.updatedAt?.toString())
                } else {
                    Label(forId = "member-user-id", attrs = { classes("form-label") }) { Text("User ID") }
                    Input(InputType.Text, attrs = {
                        id("member-user-id"); classes("form-control", "mb-3"); value(userId)
                        if (mode == "Edit") attr("readonly", "")
                        onInput { userId = it.value }
                    })
                    Label(forId = "member-role", attrs = { classes("form-label") }) { Text("Role") }
                    Input(InputType.Text, attrs = { id("member-role"); classes("form-control", "mb-3"); value(role); onInput { role = it.value } })
                    Label(forId = "member-status", attrs = { classes("form-label") }) { Text("Status") }
                    Select(attrs = { id("member-status"); classes("form-select"); onChange { status = it.value ?: "Active" } }) {
                        listOf("Active", "Disabled").forEach { value -> Option(value, attrs = { if (value == status) selected() }) { Text(value) } }
                    }
                    P(attrs = { classes("small", "text-body-secondary", "mt-3", "mb-0") }) { Text("Local draft only. No changes will be sent to the server.") }
                }
            }
            Div(attrs = { classes("p-3", "border-top", "d-flex", "justify-content-end", "gap-2") }) {
                Button(attrs = { classes("btn", "btn-outline-secondary"); attr("type", "button"); onClick { onClose() } }) { Text("Close") }
                if (!readOnly) Button(attrs = { classes("btn", "btn-primary"); attr("type", "submit") }) { Text("Save local draft") }
            }
        }
    }
}