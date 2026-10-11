package org.example.pages.company

import androidx.compose.runtime.*
import com.auth_service.model.Company
import com.auth_service.model.CrmAddress
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.onSubmit
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.*

@Composable
fun CompanyForm(
    company: Company?,
    onSubmit: ((Company) -> Unit)? = null,
    formId: String = "company-form",
) {
    var draft by remember(company) { mutableStateOf<Company>(company ?: emptyCompany()) }
    var parentId by remember(company) { mutableStateOf<String>(company?.parentCompanyId?.toString().orEmpty()) }
    var customFields by remember(company) {
        mutableStateOf<String>(Json.encodeToString(company?.customFields ?: emptyMap<String, String>()))
    }
    var error by remember(company) { mutableStateOf<String?>(null) }

    Form(attrs = {
        id(formId)
        this.onSubmit { event ->
            event.preventDefault()
            error = null;
            val parent: String? = parentId.trim().takeIf { it.isNotEmpty() }
            if(draft.name.isNullOrBlank()) {
                error = "Company name is required."
            } else if(parent != null && parent.toIntOrNull() == null) {
                error = "Parent company ID must be a whole number within the supported range."
            } else {
                val fields = try {
                    if (customFields.isBlank()) emptyMap()
                    else Json.decodeFromString<Map<String, String>>(customFields)
                } catch (_: Exception) {
                    null
                }
                if (fields == null) {
                    error = "Custom fields must be a JSON object with text keys and text values."
                } else {
                    onSubmit?.invoke(draft.copy(
                        name = draft.name?.trim(),
                        parentCompanyId = parent?.toInt(),
                        customFields = fields.takeIf { it.isNotEmpty() },
                    ))
                }
            }
        }
    }) {
        error?.let { message ->
            Div(attrs = { classes("alert", "alert-danger"); attr("role", "alert") }) { Text(message) }
        }
        H2(attrs = { classes("h5", "mb-3") }) { Text("Company details") }
        Div(attrs = { classes("row", "g-3", "mb-4") }) {
            CompanyField("$formId-name", "Company name", draft.name, required = true) { draft = draft.copy(name = it) }
            CompanyField("$formId-type", "Company type", draft.companyType) { draft = draft.copy(companyType = it) }
            CompanyField("$formId-industry", "Industry", draft.industry) { draft = draft.copy(industry = it) }
            CompanyField("$formId-status", "Status", draft.status) { draft = draft.copy(status = it) }
            CompanyField("$formId-website", "Website", draft.website, InputType.Url) { draft = draft.copy(website = it) }
            CompanyField("$formId-domain", "Email domain", draft.emailDomain) { draft = draft.copy(emailDomain = it) }
            CompanyField("$formId-phone", "Phone", draft.phone, InputType.Tel) { draft = draft.copy(phone = it) }
            CompanyField("$formId-revenue", "Annual revenue", draft.annualRevenue) { draft = draft.copy(annualRevenue = it) }
            CompanyField("$formId-currency", "Currency code", draft.currency) { draft = draft.copy(currency = it) }
        }
        H2(attrs = { classes("h5", "mb-3") }) { Text("Ownership") }
        Div(attrs = { classes("row", "g-3", "mb-4") }) {
            CompanyField("$formId-workspace", "Workspace ID", draft.workspaceId) { draft = draft.copy(workspaceId = it) }
            CompanyField("$formId-owner", "Owner user ID", draft.ownerUserId) { draft = draft.copy(ownerUserId = it) }
            CompanyField("$formId-parent", "Parent company ID", parentId) { parentId = it.orEmpty() }
        }
        AddressFields("$formId-billing", "Billing address", draft.billingAddress) { draft = draft.copy(billingAddress = it) }
        AddressFields("$formId-shipping", "Shipping address", draft.shippingAddress) { draft = draft.copy(shippingAddress = it) }
        Div(attrs = { classes("mb-3") }) {
            Label(forId = "$formId-description", attrs = { classes("form-label") }) { Text("Description") }
            TextArea(value = draft.description.orEmpty(), attrs = {
                id("$formId-description")
                classes("form-control")
                attr("rows", "3")
                onInput { draft = draft.copy(description = it.value.takeIf(String::isNotBlank)) }
            })
        }
        Div(attrs = { classes("mb-4") }) {
            Label(forId = "$formId-custom", attrs = { classes("form-label") }) { Text("Custom fields (JSON)") }
            TextArea(value = customFields, attrs = {
                id("$formId-custom")
                classes("form-control", "font-monospace")
                attr("rows", "3")
                attr("aria-describedby", "$formId-custom-help")
                onInput { customFields = it.value }
            })
            Div(attrs = { id("$formId-custom-help"); classes("form-text") }) {
                Text("Example: {\"accountTier\": \"Gold\"}. Leave empty if unused.")
            }
        }
        if (company != null) {
            H2(attrs = { classes("h5", "mb-3") }) { Text("Record information") }
            Div(attrs = { classes("row", "g-3", "mb-4") }) {
                CompanyField("$formId-id", "Company ID", company.companyId, readOnly = true)
                CompanyField("$formId-normalized", "Normalized name", company.normalizedName, readOnly = true)
                CompanyField("$formId-created", "Created at", company.createdAt?.toString(), readOnly = true)
                CompanyField("$formId-updated", "Updated at", company.updateAt?.toString(), readOnly = true)
                CompanyField("$formId-created-by", "Created by user ID", company.createdByUserId, readOnly = true)
                CompanyField("$formId-updated-by", "Updated by user ID", company.updatedByUserId, readOnly = true)
            }
        }
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
    readOnly: Boolean = false,
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
            if (readOnly) attr("readonly", "")
            onInput { onChange(it.value.takeIf(String::isNotBlank)) }
        })
    }
}

@Composable
private fun AddressFields(prefix: String, title: String, address: CrmAddress, onChange: (CrmAddress) -> Unit) {
    H2(attrs = { classes("h5", "mb-3") }) { Text(title) }
    Div(attrs = { classes("row", "g-3", "mb-4") }) {
        CompanyField("$prefix-line1", "Address line 1", address.line1) { onChange(address.copy(line1 = it)) }
        CompanyField("$prefix-line2", "Address line 2", address.line2) { onChange(address.copy(line2 = it)) }
        CompanyField("$prefix-city", "City", address.city) { onChange(address.copy(city = it)) }
        CompanyField("$prefix-region", "State / region", address.region) { onChange(address.copy(region = it)) }
        CompanyField("$prefix-postal", "Postal code", address.postalCode) { onChange(address.copy(postalCode = it)) }
        CompanyField("$prefix-country", "Country code", address.countryCode) { onChange(address.copy(countryCode = it)) }
    }
}

private fun emptyCompany() = Company(
    workspaceId = null, companyId = null, name = null, normalizedName = null,
    companyType = null, industry = null, website = null, emailDomain = null,
    phone = null, ownerUserId = null, parentCompanyId = null, annualRevenue = null,
    currency = null, billingAddress = CrmAddress(null, null, null, null, null, null),
    shippingAddress = CrmAddress(null, null, null, null, null, null), description = null,
    status = null, customFields = null, createdAt = null, updateAt = null,
    createdByUserId = null, updatedByUserId = null,
)
