package pages.company

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.auth_service.model.CrmAddress
import com.varabyte.kobweb.browser.api
import com.varabyte.kobweb.browser.http.bodyAsBytes
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import org.example.pages.company.AddCompanyModal
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

private data class TableCompany(
    val name: String,
    val address: CrmAddress,
    val phone: String
)

@Page(routeOverride = "/company")
@Composable
fun CompanyListPage() {
    val scope: CoroutineScope = rememberCoroutineScope()

    var loadTable by remember { mutableStateOf<Boolean>(true) }
    var tableCompanies by remember { mutableStateOf<List<TableCompany>>(emptyList()) }

    var loading by remember { mutableStateOf<Boolean>(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedId by remember { mutableStateOf<String?>(null) }



    LaunchedEffect(Unit) {
        try {
            val response = window.api.get("/company/get/all").bodyAsBytes().decodeToString();
        } catch(e: Exception) {

        } finally {
            loadTable = false;
        }
    }

    val columns: Array<String> = arrayOf("Name", "Address", "Phone")
    Div(attrs = {
        classes("row")
    }) {
        Div(attrs = {
            classes("col-md-12", "card")
        }) {
            Div(attrs = {
                classes("card-body")
            }) {
                Button(attrs = {
                    id("add-company-button")
                    classes("btn", "btn-primary")
                    attr("type", "button")
                    attr("data-bs-toggle", "modal")
                    attr("data-bs-target", "#add-company-modal")
                }) { Text("Add Company") }
                Table(attrs = {
                    classes("table")
                }) {
                    Thead {
                        Tr {
                            Th(attrs = {
                                colspan(value = columns.size + 1)
                            }) {
                                Div(attrs = {
                                    classes("d-flex", "justify-content-between", "align-items-center")
                                }) {
                                    H1 { Text("Companies") }
                                }
                            }
                        }
                        Tr {
                            Th {
                                Text("Actions")
                            }
                            for((i, column) in columns.withIndex()) {
                                Th {
                                    Text(column)
                                }
                            }
                        }
                    }

                    Tbody {
                        if(loadTable) {
                            Tr {
                                Td(attrs = {
                                    colspan(value = columns.size + 1)
                                }) {
                                    Div(attrs = {
                                        classes("spinner-border", "spinner-border-sm")
                                    }) {
                                        Span(attrs = {
                                            classes("visually-hidden")
                                        }) {
                                            Text("Loading...")
                                        }
                                    }
                                    P(attrs = {
                                        classes("mb-0", "mb-2", "text-muted")
                                    }) {
                                        Text("Loading data...")
                                    }
                                }
                            }
                            return@Tbody
                        }

                        for(company in tableCompanies) {
                            Tr {
                                Td {
                                    Text(company.name)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    AddCompanyModal();
}