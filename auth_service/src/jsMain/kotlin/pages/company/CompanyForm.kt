package org.example.pages.company

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.model.CrmAddress
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.dom.*

@Composable
fun CompanyForm(
    company: Company?,
    onSubmit: ((Company) -> Unit)? = null,
    formId: String = "company-form",
) {
    var draft by remember(company) { mutableStateOf(company ?: Company(billingAddress = CrmAddress())) }
    var error by remember(company) { mutableStateOf<String?>(null) }

    Form(attrs = {
        id(formId)
        this.onSubmit { event ->
            event.preventDefault()
            if (draft.name.isNullOrBlank()) {
                error = "Company name is required."
            } else {
                error = null
                onSubmit?.invoke(draft.copy(name = draft.name?.trim()))
            }
        }
    }) {
        error?.let { message ->
            Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(message) }
        }
        H2(attrs = { classes("h5", "mb-3") }) { Text("Business account") }
        Div(attrs = { classes("row", "g-3", "mb-4") }) {
            CompanyField("$formId-name", "Company name", draft.name, required = true) { draft = draft.copy(name = it) }
            CompanyField("$formId-legal-name", "Legal business name", draft.legalName) { draft = draft.copy(legalName = it) }
            CompanyField("$formId-billing-email", "Billing email", draft.billingEmail, InputType.Email) { draft = draft.copy(billingEmail = it) }
            CompanyField("$formId-phone", "Phone", draft.phone, InputType.Tel) { draft = draft.copy(phone = it) }
            CompanyField("$formId-website", "Website", draft.website, InputType.Url) { draft = draft.copy(website = it) }
            Div(attrs = { classes("col-12", "col-md-6") }) {
                Label(forId = "$formId-status", attrs = { classes("form-label") }) { Text("Record status") }
                Select(attrs = {
                    id("$formId-status")
                    classes("form-select")
                    onChange { draft = draft.copy(status = it.value) }
                }) {
                    for (status in listOf("Active", "Disabled")) {
                        Option(value = status, attrs = { if (draft.status == status) selected() }) { Text(status) }
                    }
                }
            }
        }
        AddressFields("$formId-billing", draft.billingAddress ?: CrmAddress()) { draft = draft.copy(billingAddress = it) }
        if (onSubmit != null) {
            Button(attrs = { classes("btn", "btn-primary"); attr("type", "submit") }) {
                Text(if (company == null) "Add company" else "Save changes")
            }
        }
    }
}

@Composable
private fun CompanyField(
    fieldId: String,
    label: String,
    fieldValue: String?,
    inputType: InputType<String> = InputType.Text,
    required: Boolean = false,
    onChange: (String?) -> Unit = {},
) {
    Div(attrs = { classes("col-12", "col-md-6") }) {
        Label(forId = fieldId, attrs = { classes("form-label") }) { Text(label) }
        Input(type = inputType, attrs = {
            id(fieldId)
            attr("name", fieldId)
            classes("form-control")
            value(fieldValue.orEmpty())
            if (required) attr("required", "")
            onInput { onChange(it.value.takeIf(String::isNotBlank)) }
        })
    }
}

@Composable
private fun AddressFields(prefix: String, address: CrmAddress, onChange: (CrmAddress) -> Unit) {
    H2(attrs = { classes("h5", "mb-3") }) { Text("Billing address") }
    Div(attrs = { classes("row", "g-3", "mb-4") }) {
        CompanyField("$prefix-line1", "Address line 1", address.line1) { onChange(address.copy(line1 = it)) }
        CompanyField("$prefix-line2", "Address line 2", address.line2) { onChange(address.copy(line2 = it)) }
        CompanyField("$prefix-city", "City", address.city) { onChange(address.copy(city = it)) }
        CompanyField("$prefix-region", "State / region", address.region) { onChange(address.copy(region = it)) }
        CompanyField("$prefix-postal", "Postal code", address.postalCode) { onChange(address.copy(postalCode = it)) }
        CompanyField("$prefix-country", "Country code", address.countryCode) { onChange(address.copy(countryCode = it)) }
    }
}
