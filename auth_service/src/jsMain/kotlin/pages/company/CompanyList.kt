package pages.company

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.colspan
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.I
import org.jetbrains.compose.web.dom.Table
import org.jetbrains.compose.web.dom.Tbody
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Th
import org.jetbrains.compose.web.dom.Thead
import org.jetbrains.compose.web.dom.Tr

import com.auth_service.model.Company

@Page(routeOverride = "/company")
@Composable
fun CompanyListPage() {
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
                Table(attrs = {
                    classes("table")
                }) {
                    Thead {
                        Tr {
                            Th(attrs = {
                                colspan(value = columns.size + 2)
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
                                I()
                            }
                            for((i, column) in columns.withIndex()) {
                                Th {
                                    Text(column)
                                }
                            }
                            Th {
                                Button(attrs = {
                                    id("add-company-button")
                                    classes("btn", "btn-primary")
                                    attr("type", "button")
                                    attr("data-bs-toggle", "modal")
                                    attr("data-bs-target", "#add-company-modal")
                                    onClick { console.log("Add Company") }
                                }) { Text("Add Company") }
                            }
                        }
                    }

                    Tbody {

                    }
                }
            }
        }
    }

    AddCompanyModal();
}

@Composable
fun AddCompanyModal() {
    Div(attrs = {
        id("add-company-modal")
        classes("modal", "fade")
        attr("tabindex", "-1")
        attr("aria-labelledby", "add-company-title")
        attr("aria-hidden", "true")
    }) {
        Div(attrs = { classes("modal-dialog") }) {
            Div(attrs = { classes("modal-content") }) {
                Div(attrs = { classes("modal-header") }) {
                    H1(attrs = {
                        id("add-company-title")
                        classes("modal-title", "fs-5")
                    }) {
                        Text("Add company")
                    }

                    Button(attrs = {
                        classes("btn-close")
                        attr("type", "button")
                        attr("data-bs-dismiss", "modal")
                        attr("aria-label", "Close")
                    })
                }

                Div(attrs = { classes("modal-body") }) {
                    // Place your company form fields here.
                    Text("Company details")
                }

                Div(attrs = { classes("modal-footer") }) {
                    Button(attrs = {
                        classes("btn", "btn-secondary")
                        attr("type", "button")
                        attr("data-bs-dismiss", "modal")
                    }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}
