package org.example.pages.company

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.auth_service.model.Company
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import com.auth_service.serialization.AppJson
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Text
import org.w3c.fetch.RequestInit
import kotlin.js.json

@Composable
fun EditCompanyModal(company: Company?): Unit {
    val scope: CoroutineScope = rememberCoroutineScope()

    Div(attrs = {
        id("edit-company-modal")
        classes("modal", "fade")
        attr("tabindex", "-1")
        attr("aria-labelledby", "edit-company-title")
        attr("aria-hidden", "true")
    }) {
        Div(attrs = { classes("modal-dialog", "modal-lg") }) {
            Div(attrs = { classes("modal-content") }) {
                Div(attrs = { classes("modal-header") }) {
                    H1(attrs = {
                        id("edit-company-title")
                        classes("modal-title", "fs-5")
                    }) {
                        Text("Edit ${company?.name}")
                    }

                    Button(attrs = {
                        classes("btn-close")
                        attr("type", "button")
                        attr("data-bs-dismiss", "modal")
                        attr("aria-label", "Close")
                    })
                }

                Div(attrs = { classes("modal-body") }) {
                    CompanyForm(
                        company = company,
                        onSubmit = { company ->
                            scope.launch {
                                try {
                                    val response = window.fetch(
                                        "/api/company/save",
                                        RequestInit(
                                            method = "PUT",
                                            headers = json("Content-Type" to "application/json"),
                                            body = AppJson.encodeToString(company),
                                        ),
                                    ).await()
                                    val body: dynamic = response.json().await().asDynamic()

                                } catch(e: Exception) {}
                            }

                        })
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