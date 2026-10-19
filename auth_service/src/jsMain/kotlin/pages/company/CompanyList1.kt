package pages.company

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.auth_service.model.Company
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import com.auth_service.serialization.AppJson
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import org.example.pages.company.AddCompanyModal
import org.example.pages.company.EditCompanyModal
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Td
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Thead
import org.jetbrains.compose.web.dom.Tr
import org.w3c.fetch.Response


@Page(routeOverride = "/company2")
@Composable
fun CompanyList1Page() {
    val scope: CoroutineScope = rememberCoroutineScope()

    var loadTable by remember { mutableStateOf<Boolean>(false) }
    var allCompanies by remember { mutableStateOf<List<Company>>(emptyList()) }

    var error by remember { mutableStateOf<String?>(null) }
    var selectedCompany by remember { mutableStateOf<Company?>(null) }

    LaunchedEffect(Unit) {
        try {
            loadTable = true;
            val response: Response = window.api.get("/company/get/all");

            val body: JsonObject = AppJson.parseToJsonElement(
                response.bodyAsBytes().decodeToString()
            ).jsonObject

            val data = body["data"] ?: throw Exception("Data not found in response")

            allCompanies = AppJson.decodeFromJsonElement<List<Company>>(data)
        } catch(e: Exception) {}
        finally {
            loadTable = false;
        }
    }

    val columns: Array<String> = arrayOf("Name", "Billing address", "Phone")
    Div(attrs = {
        classes("container", "py-4", "px-3", "mx-auto")
        attr("style", "max-width: 1080px;")
    }) {
        Div(attrs = {
            classes("card", "border", "shadow-sm", "rounded-3")
        }) {
            Div(attrs = {
                classes("card-body", "p-3", "p-md-4")
            }) {
                Div(attrs = {
                    classes("d-flex", "flex-column", "flex-sm-row", "justify-content-between", "align-items-sm-center", "gap-3", "mb-4")
                }) {
                    Div {
                        H1(attrs = { classes("h3", "fw-semibold", "mb-1") }) { Text("Companies") }
                        P(attrs = { classes("text-body-secondary", "mb-0", "small") }) {
                            Text("Manage your company directory and contact details.")
                        }
                    }
                    Button(attrs = {
                        id("add-company-button")
                        classes("btn", "btn-primary", "px-3", "py-2", "text-nowrap", "align-self-start", "align-self-sm-center")
                        attr("type", "button")
                        attr("data-bs-toggle", "modal")
                        attr("data-bs-target", "#add-company-modal")
                    }) { Text("Add Company") }
                }
                Div(attrs = { classes("table-responsive", "border", "rounded-3") }) {
                    Table(attrs = {
                        classes("table", "table-sm", "table-hover", "align-middle", "mb-0")
                    }) {
                        Thead(attrs = { classes("table-light", "small", "text-nowrap") }) {
                            Tr {
                                Th(attrs = { classes("px-3", "py-3"); attr("scope", "col") }) {
                                    Text("Actions")
                                }
                                for((i, column) in columns.withIndex()) {
                                    Th(attrs = { classes("px-3", "py-3"); attr("scope", "col") }) {
                                        Text(column)
                                    }
                                }
                            }
                        }

                        Tbody {
                            if(loadTable) {
                                Tr {
                                    Td(attrs = {
                                        classes("text-center", "py-5", "text-body-secondary")
                                        colspan(value = columns.size + 1)
                                    }) {
                                        Div(attrs = {
                                            classes("spinner-border", "spinner-border-sm", "text-primary", "mb-2")
                                            attr("role", "status")
                                        }) {
                                            Span(attrs = {
                                                classes("visually-hidden")
                                            }) {
                                                Text("Loading...")
                                            }
                                        }
                                        P(attrs = {
                                            classes("mb-0", "small", "text-body-secondary")
                                        }) {
                                            Text("Loading data...")
                                        }
                                    }
                                }
                                return@Tbody
                            }

                            for(company: Company in allCompanies) {
                                Tr {
                                    Td(attrs = { classes("px-3", "py-3") }) {
                                        Div(attrs = { classes("d-flex", "gap-2", "align-items-center", "text-nowrap") }) {
                                            Button(attrs = {
                                                classes("btn", "btn-sm", "btn-outline-primary")
                                                onClick {
                                                    selectedCompany = company
                                                }
                                                attr("data-bs-toggle", "modal")
                                                attr("data-bs-target", "#edit-company-modal")
                                            }) {
                                                Text("Edit")
                                            }
                                            Button(attrs = {
                                                classes("btn", "btn-sm", "btn-outline-secondary")
                                                onClick {
                                                    window.location.assign("/company/id/${company.companyId}")
                                                }
                                            }) {
                                                Text("View")
                                            }
                                        }
                                    }
                                    Td(attrs = { classes("px-3", "py-3") }) {
                                        company.name?.let { Text(it) }
                                    }
                                    Td(attrs = { classes("px-3", "py-3") }) {
                                        Text(company.billingAddress.toString())
                                    }
                                    Td(attrs = { classes("px-3", "py-3") }) {
                                        company.phone?.let { Text(it) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    AddCompanyModal();
    EditCompanyModal(company = selectedCompany)
}
