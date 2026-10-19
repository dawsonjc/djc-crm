package pages.company

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import com.varabyte.kobweb.core.Page
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import com.auth_service.serialization.AppJson
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.example.pages.company.AddCompanyModal
import org.example.pages.company.EditCompanyModal
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLElement
import kotlin.js.json

@JsName("DataTable")
private external class CompanyTable(element: HTMLElement, options: dynamic) {
    fun destroy(remove: Boolean)
}

@Page(routeOverride = "/company")
@Composable
fun CompanyListPage() {
    var companies by remember { mutableStateOf<List<Company>>(emptyList()) }
    var loading by remember { mutableStateOf<Boolean>(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var revision by remember { mutableStateOf<Int>(0) }
    var selectedCompany by remember { mutableStateOf<Company?>(null) }

    LaunchedEffect(revision) {
        loading = true
        error = null
        try {
            val response = window.api.get("/company/get/all")
            check(response.ok) { "Could not load companies (HTTP ${response.status})." }
            val body = AppJson.parseToJsonElement(response.bodyAsBytes().decodeToString()).jsonObject
            companies = AppJson.decodeFromJsonElement<List<Company>>(
                body["data"] ?: error("Company response is missing data.")
            )
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            error = e.message ?: "Could not load companies. Please try again."
        } finally {
            loading = false
        }
    }

    Div(attrs = { classes("container", "p-3", "p-lg-4", "mx-auto"); attr("style", "max-width: 1080px;") }) {
        Div(attrs = { classes("d-flex", "flex-wrap", "justify-content-between", "align-items-center", "gap-3", "mb-4") }) {
            Div {
                Span(attrs = { classes("badge", "text-bg-primary", "mb-2") }) { Text("COMPANY DIRECTORY") }
                H1(attrs = { classes("h2", "fw-semibold", "mb-1") }) { Text("Companies") }
                P(attrs = { classes("text-body-secondary", "mb-0") }) {
                    Text("Find a business, review its details, and keep your contacts up to date.")
                }
            }
            Div(attrs = { classes("d-flex", "gap-2") }) {
                Button(attrs = {
                    classes("btn", "btn-outline-secondary")
                    attr("type", "button")
                    if (loading) attr("disabled", "")
                    onClick { revision++ }
                }) { Text("Refresh") }
                Button(attrs = {
                    id("add-company-button")
                    classes("btn", "btn-primary", "px-4")
                    attr("type", "button")
                    attr("data-bs-toggle", "modal")
                    attr("data-bs-target", "#add-company-modal")
                }) { Text("+ Add Company") }
            }
        }
        Div(attrs = { classes("card", "shadow-sm", "rounded-3") }) {
            Div(attrs = { classes("card-header", "bg-body", "p-3", "d-flex", "justify-content-between", "align-items-center") }) {
                Span(attrs = { classes("fw-semibold") }) { Text("All companies") }
                Span(attrs = { classes("badge", "text-bg-light", "border") }) {
                    Text(if (loading) "Loading…" else "${companies.size} records")
                }
            }
            Div(attrs = { classes("card-body", "p-3", "p-lg-4") }) {
                when {
                    loading -> Div(attrs = { classes("text-center", "py-5"); attr("role", "status") }) {
                        Div(attrs = { classes("spinner-border", "text-primary", "mb-3"); attr("aria-hidden", "true") })
                        P(attrs = { classes("text-body-secondary", "mb-0") }) { Text("Loading companies…") }
                    }
                    error != null -> Div(attrs = { classes("alert", "alert-danger", "mb-0"); attr("role", "alert") }) {
                        Text(error!!)
                    }
                    else -> key(revision) {
                        CompanyDirectoryTable(companies, onEdit = { selectedCompany = it })
                    }
                }
            }
            Div(attrs = { classes("card-footer", "bg-body", "small", "text-body-secondary", "p-3") }) {
                Text("Search across company details • Select a column heading to sort")
            }
        }
    }
    AddCompanyModal()
    EditCompanyModal(company = selectedCompany)
}

@Composable
private fun CompanyDirectoryTable(companies: List<Company>, onEdit: (Company) -> Unit) {
    // Compose owns only this host. DataTables owns every element inside it.
    Div(attrs = {
        classes("overflow-auto")
        ref { host ->
            val table = document.createElement("table") as HTMLElement
            table.className = "table table-sm table-hover align-middle w-100"
            table.setAttribute("aria-label", "Company directory")
            val head = document.createElement("thead")
            head.className = "table-light"
            val headings = document.createElement("tr")
            listOf("Company", "Billing address", "Phone", "Actions").forEach { label ->
                val cell = document.createElement("th")
                cell.setAttribute("scope", "col")
                cell.textContent = label
                headings.appendChild(cell)
            }
            head.appendChild(headings)
            table.appendChild(head)
            val body = document.createElement("tbody")
            companies.forEach { company ->
                val row = document.createElement("tr")
                val address = company.billingAddress?.let {
                    listOfNotNull(it.line1, it.line2, it.city, it.region, it.postalCode, it.countryCode)
                        .filter { part -> part.isNotBlank() }.joinToString(", ")
                }.orEmpty()
                listOf(company.name.orEmpty(), address, company.phone.orEmpty()).forEachIndexed { index, value ->
                    val cell = document.createElement("td")
                    cell.className = if (index == 0) "fw-semibold py-3" else "text-body-secondary py-3"
                    // Text nodes keep company-provided content out of HTML parsing.
                    cell.textContent = value.ifBlank { "—" }
                    row.appendChild(cell)
                }
                val actions = document.createElement("td")
                actions.className = "text-nowrap"
                val edit = document.createElement("button") as HTMLElement
                edit.className = "btn btn-sm btn-outline-primary me-2"
                edit.textContent = "Edit"
                edit.setAttribute("type", "button")
                edit.setAttribute("data-bs-toggle", "modal")
                edit.setAttribute("data-bs-target", "#edit-company-modal")
                edit.onclick = { onEdit(company); Unit }
                actions.appendChild(edit)
                val view = document.createElement("button") as HTMLElement
                view.className = "btn btn-sm btn-outline-secondary"
                view.textContent = "View"
                view.setAttribute("type", "button")
                view.onclick = { window.location.assign("/company/id/${company.companyId}"); Unit }
                actions.appendChild(view)
                row.appendChild(actions)
                body.appendChild(row)
            }
            table.appendChild(body)
            host.appendChild(table)
            var instance: CompanyTable? = null
            try {
                instance = CompanyTable(table, json(
                    "pageLength" to 10,
                    "lengthMenu" to arrayOf(10, 25, 50),
                    "order" to arrayOf(arrayOf(0, "asc")),
                    "columnDefs" to arrayOf(json("targets" to 3, "orderable" to false, "searchable" to false)),
                    "language" to json(
                        "search" to "Search companies:",
                        "searchPlaceholder" to "Name, address, or phone",
                        "emptyTable" to "No companies yet. Add your first company to get started.",
                        "zeroRecords" to "No companies match your search.",
                    ),
                ))
            } catch (e: Exception) {
                val notice = document.createElement("p")
                notice.className = "alert alert-warning"
                notice.setAttribute("role", "alert")
                notice.textContent = "Table tools could not load. Showing the basic company list; try reloading the page."
                host.insertBefore(notice, table)
            }
            onDispose {
                instance?.destroy(true)
                host.textContent = ""
            }
        }
    })
}