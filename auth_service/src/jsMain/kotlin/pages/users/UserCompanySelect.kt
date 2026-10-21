package org.example.pages.users

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.serialization.AppJson
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import kotlinx.browser.window
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.web.dom.*
import kotlin.uuid.Uuid

/** Uses the existing company list API; reassignment is returned through the form callback. */
@Composable
internal fun UserCompanySelect(
    companyId: Uuid?, companyName: String?, defaultCompany: Company, onChange: (Company) -> Unit,
) {
    var companies by remember { mutableStateOf<List<Company>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var revision by remember { mutableStateOf(0) }
    val currentOnChange by rememberUpdatedState(onChange)
    val currentId by rememberUpdatedState(companyId)

    LaunchedEffect(revision) {
        loading = true
        error = null
        try {
            val response = window.api.get("/company/get/all")
            check(response.ok) { "Could not load companies (HTTP ${response.status})." }
            val body = AppJson.parseToJsonElement(response.bodyAsBytes().decodeToString()).jsonObject
            companies = AppJson.decodeFromJsonElement<List<Company>>(
                body["data"] ?: error("Company list response is missing data.")
            )
            // Refresh the saved display name from the catalog without changing the assignment.
            companies.firstOrNull { it.companyId == currentId }?.let { currentOnChange(it) }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            error = e.message ?: "Could not load companies."
        } finally {
            loading = false
        }
    }
    val saved = companyId?.let { Company(companyId = it, name = companyName) }
    val choices = (companies + defaultCompany + listOfNotNull(saved))
        .filter { it.companyId != null }.distinctBy { it.companyId }

    SearchableUserSelect(
        id = "user-company", label = "Company", value = companyId?.toString().orEmpty(),
        options = listOf("" to "Select a company") + choices.map {
            it.companyId.toString() to "${it.name ?: "Unnamed company"} (${it.companyId})"
        },
    ) { selected ->
        if (selected.isNotEmpty()) choices.firstOrNull { it.companyId.toString() == selected }?.let(onChange)
        else onChange(Company())
    }
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Label(attrs = { classes("form-label") }) { Text("Company ID") }
        P(attrs = { classes("form-control-plaintext", "small", "text-break", "mb-0") }) {
            Text(companyId?.toString() ?: "Select a company")
        }
        if (loading) P(attrs = { classes("form-text"); attr("role", "status") }) { Text("Loading available companies…") }
        error?.let { message ->
            Div(attrs = { classes("small", "text-danger"); attr("role", "alert") }) { Text(message) }
            Button(attrs = {
                classes("btn", "btn-link", "btn-sm", "px-0"); attr("type", "button")
                onClick { revision++ }
            }) { Text("Retry loading companies") }
        }
    }
}