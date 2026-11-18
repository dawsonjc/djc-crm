package org.example.pages.company

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.model.CompanyUsersPage
import com.auth_service.model.CompanyUser
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import org.w3c.fetch.RequestInit
import kotlin.js.json
import com.auth_service.serialization.AppJson
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.web.attributes.*
import org.jetbrains.compose.web.dom.*
import org.example.pages.users.AddUserModal
import org.example.pages.users.EditUserModal
import org.example.pages.users.DeleteUserModal
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
            else -> company?.let { key(it.companyId) { CompanyDetails(it) } }
        }
    }
}

@Composable
private fun CompanyDetails(company: Company) {
    var users by remember { mutableStateOf<List<CompanyUser>>(emptyList()) }
    var dialog by remember { mutableStateOf<String?>(null) }
    var selectedUser by remember { mutableStateOf<CompanyUser?>(null) }
    val scope = rememberCoroutineScope()
    var savingUser by remember { mutableStateOf(false) }
    var saveError by remember { mutableStateOf<String?>(null) }
    var notice by remember { mutableStateOf<String?>(null) }
    var deliveryWarning by remember { mutableStateOf(false) }

    var page by remember { mutableStateOf(0) }
    var pageSize by remember { mutableStateOf(10) }
    var revision by remember { mutableStateOf(0) }
    var usersLoading by remember { mutableStateOf(true) }
    var usersError by remember { mutableStateOf<String?>(null) }
    var pageData by remember { mutableStateOf<CompanyUsersPage?>(null) }

    LaunchedEffect(company.companyId, page, pageSize, revision) {
        usersLoading = true
        usersError = null
        users = emptyList()
        pageData = null
        try {
            val response = window.fetch(
                "/api/companies/${company.companyId}/users?page=$page&size=$pageSize",
                RequestInit(headers = json("Accept" to "application/json")),
            ).await()
            val text = response.text().await()
            val body = runCatching { AppJson.parseToJsonElement(text).jsonObject }.getOrNull()
            check(response.ok && (body?.get("success") as? JsonPrimitive)?.booleanOrNull == true) {
                if (response.status.toInt() == 401) "Please sign in again to load company users."
                else (body?.get("message") as? JsonPrimitive)?.contentOrNull?.takeIf(String::isNotBlank)
                    ?: "Could not load company users (HTTP ${response.status})."
            }
            val result = AppJson.decodeFromJsonElement<CompanyUsersPage>(
                body["data"] ?: error("The users response is missing page data.")
            )
            check(result.number == page && result.size == pageSize) { "The server returned an unexpected users page." }
            currentCoroutineContext().ensureActive()
            // A reassignment or deletion may empty the last page. Step back and reload.
            if (result.content.isEmpty() && page > 0) {
                page = (result.totalPages?.minus(1) ?: (page - 1)).coerceIn(0, page - 1)
            } else {
                users = result.content
                pageData = result
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            usersError = e.message ?: "Could not load company users. Please try again."
        } finally {
            if (currentCoroutineContext().isActive) usersLoading = false
        }
    }
    val submitUser: (CompanyUser, Boolean) -> Unit = { draft, editing ->
        if (!savingUser) {
            if (editing && draft.userId == null) {
                saveError = "This user has no ID and cannot be updated."
            } else {
                savingUser = true
                saveError = null
                scope.launch {
                    try {
                        val response = window.fetch(if (editing) "/api/user/save" else "/api/user/add", RequestInit(
                            method = if (editing) "PUT" else "POST",
                            headers = json("Content-Type" to "application/json", "Accept" to "application/json"),
                            body = AppJson.encodeToString(draft),
                        )).await()
                        val text = response.text().await()
                        val result = runCatching { AppJson.parseToJsonElement(text).jsonObject }.getOrNull()
                        if (!response.ok || (result?.get("success") as? JsonPrimitive)?.booleanOrNull != true) {
                            val message = (result?.get("message") as? JsonPrimitive)?.contentOrNull?.takeIf { it.isNotBlank() }
                            val fields = (result?.get("errors") as? JsonObject ?: result?.get("data") as? JsonObject)
                                ?.values?.mapNotNull { (it as? JsonPrimitive)?.contentOrNull }?.joinToString(" ")
                            saveError = if (response.status.toInt() == 401) "Please sign in again before saving a user."
                                else listOfNotNull(message, fields?.takeIf { it.isNotBlank() }).joinToString(" ")
                                    .ifBlank { "Could not save user (HTTP ${response.status}). Please try again." }
                        } else {
                            // Both endpoints must return the persisted user, including its user/company IDs.
                            val saved = result["data"]?.let { AppJson.decodeFromJsonElement<CompanyUser>(it) }
                            check(saved?.userId != null && saved.companyId == draft.companyId
                                && (!editing || saved.userId == draft.userId)) {
                                "The server did not return a saved user with the expected user and company IDs. Check whether it was saved before retrying."
                            }
                            // Reload the server page to preserve its ordering, size and membership.
                            if (!editing) page = 0
                            revision++
                            val savedMessage = when {
                                saved.companyId != company.companyId -> "User ${if (editing) "reassigned to" else "created for"} ${saved.companyName ?: draft.companyName ?: "the selected company"}."
                                editing -> "User updated successfully."
                                else -> "User created successfully."
                            }
                            val mailWarning = (result["message"] as? JsonPrimitive)?.contentOrNull
                                ?.takeIf { !editing && it.isNotBlank() }
                            deliveryWarning = mailWarning != null
                            notice = mailWarning ?: savedMessage
                            dialog = null
                        }
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        saveError = e.message ?: "Could not save user. Please check your connection and try again."
                    } finally {
                        savingUser = false
                    }
                }
            }
        }
    }
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
            H2(attrs = { classes("h5", "mb-0") }) { Text(pageData?.totalElements?.let { "Company users ($it)" } ?: "Company users") }
            Button(attrs = {
                classes("btn", "btn-primary", "btn-sm")
                attr("type", "button")
                onClick { selectedUser = null; saveError = null; notice = null; dialog = "Add" }
            }) { Text("+ Add user") }
        }
        Div(attrs = { classes("card-body") }) {
            P(attrs = { classes("small", "text-body-secondary"); attr("role", "status") }) {
                Text("Browse users assigned to this company.")
            }
            notice?.let {
                Div(attrs = { classes("alert", if (deliveryWarning) "alert-warning" else "alert-success"); attr("role", "status") }) {
                    Text(it)
                }
            }
            Div(attrs = { classes("d-flex", "flex-wrap", "justify-content-between", "align-items-center", "gap-3", "mb-3") }) {
                Div(attrs = { classes("d-flex", "align-items-center", "gap-2") }) {
                    Label(forId = "company-users-size", attrs = { classes("small", "text-nowrap") }) { Text("Users per page") }
                    Select(attrs = {
                        id("company-users-size"); classes("form-select", "form-select-sm")
                        if (usersLoading || savingUser) attr("disabled", "")
                        onChange { event -> event.value?.toIntOrNull()?.let { pageSize = it; page = 0 } }
                    }) {
                        listOf(10, 25, 50).forEach { count ->
                            Option(count.toString(), attrs = { if (count == pageSize) selected() }) { Text(count.toString()) }
                        }
                    }
                }
                Button(attrs = {
                    classes("btn", "btn-sm", "btn-outline-secondary"); attr("type", "button")
                    if (usersLoading || savingUser) attr("disabled", "")
                    onClick { revision++ }
                }) { Text("Refresh users") }
            }
            usersError?.let { message ->
                Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) {
                    Text(message)
                    Button(attrs = {
                        classes("btn", "btn-sm", "btn-outline-danger", "ms-2"); attr("type", "button")
                        onClick { revision++ }
                    }) { Text("Retry") }
                }
            }
            Div(attrs = { classes("table-responsive") }) {
                Table(attrs = { classes("table", "table-sm", "table-hover", "align-middle", "mb-0") }) {
                    Thead(attrs = { classes("table-light") }) {
                        Tr { listOf("Name / Username", "Email", "Status", "Verification", "Last login", "Actions").forEach { label ->
                            Th(attrs = { attr("scope", "col") }) { Text(label) }
                        } }
                    }
                    Tbody {
                        if (usersLoading) Tr {
                            Td(attrs = { colspan(6); classes("text-center", "py-4"); attr("role", "status") }) {
                                Text("Loading company users…")
                            }
                        }
                        if (!usersLoading && usersError == null && users.isEmpty()) Tr {
                            Td(attrs = { colspan(6); classes("text-center", "text-body-secondary", "py-4") }) {
                                Text("No users are assigned to this company. Add a user to get started.")
                            }
                        }
                        users.forEach { user ->
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
                                    listOf("Edit", "View", "Delete").forEach { action ->
                                        Button(attrs = {
                                            classes("btn", "btn-sm", "me-2", when (action) { "Edit" -> "btn-outline-primary"; "Delete" -> "btn-outline-danger"; else -> "btn-outline-secondary" })
                                            attr("type", "button")
                                            onClick { selectedUser = user; saveError = null; notice = null; dialog = action }
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
    Div(attrs = {
        classes("d-flex", "flex-wrap", "justify-content-between", "align-items-center", "gap-3", "mt-3")
        attr("aria-label", "Company users pagination")
    }) {
        P(attrs = { classes("small", "text-body-secondary", "mb-0"); attr("role", "status") }) {
            val result = pageData
            Text(when {
                usersLoading -> "Loading page ${page + 1}…"
                usersError != null -> "Page ${page + 1} could not be loaded."
                result == null || users.isEmpty() -> "0 users"
                else -> "Showing ${page.toLong() * pageSize + 1}–${page.toLong() * pageSize + users.size}" +
                    (result.totalElements?.let { " of $it users" } ?: " users") + " · Page ${page + 1}" +
                    (result.totalPages?.let { " of $it" } ?: "")
            })
        }
        Div(attrs = { classes("btn-group"); attr("role", "group"); attr("aria-label", "Page navigation") }) {
            Button(attrs = {
                classes("btn", "btn-sm", "btn-outline-secondary"); attr("type", "button")
                if (page == 0 || usersLoading || savingUser) attr("disabled", "")
                onClick { page-- }
            }) { Text("Previous") }
            Button(attrs = {
                classes("btn", "btn-sm", "btn-outline-secondary"); attr("type", "button")
                if (pageData?.last != false || usersLoading || usersError != null || savingUser) attr("disabled", "")
                onClick { page++ }
            }) { Text("Next") }
        }
    }
    when (dialog) {
        "Add" -> AddUserModal(
            company = company, submitting = savingUser, submissionError = saveError,
            onClose = { if (!savingUser) dialog = null },
            onSubmit = { submitUser(it, false) },
        )
        "Edit" -> selectedUser?.let { user ->
            EditUserModal(
                user, company = company, submitting = savingUser, submissionError = saveError,
                onClose = { if (!savingUser) dialog = null },
                onSubmit = { submitUser(it, true) },
            )
        }
        "Delete" -> selectedUser?.let { user ->
            DeleteUserModal(user, savingUser, saveError,
                onClose = { if (!savingUser) dialog = null },
                onConfirm = {
                    if (!savingUser && user.userId != null) {
                        savingUser = true
                        saveError = null
                        scope.launch {
                            try {
                                val response = window.fetch(
                                    "/api/companies/${company.companyId}/users/${user.userId}",
                                    RequestInit(method = "DELETE", headers = json("Accept" to "application/json")),
                                ).await()
                                val body = runCatching { AppJson.parseToJsonElement(response.text().await()).jsonObject }.getOrNull()
                                check(response.ok && (body?.get("success") as? JsonPrimitive)?.booleanOrNull == true) {
                                    (body?.get("message") as? JsonPrimitive)?.contentOrNull
                                        ?: "Could not delete user (HTTP ${response.status})."
                                }
                                notice = "User deleted."
                                dialog = null
                                revision++
                            } catch (e: CancellationException) {
                                throw e
                            } catch (e: Exception) {
                                saveError = e.message ?: "Could not delete user. Please try again."
                            } finally {
                                savingUser = false
                            }
                        }
                    }
                },
            )
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
