package org.example.pages.users

import androidx.compose.runtime.Composable
import com.auth_service.model.Company
import com.auth_service.model.CompanyUser

@Composable
fun AddUserModal(company: Company, onClose: () -> Unit, onSubmit: (CompanyUser) -> Unit, submitting: Boolean = false, submissionError: String? = null) {
    UserModal("Add user", onClose = { if (!submitting) onClose() }) {
        UserForm(user = null, defaultCompany = company, onCancel = onClose, onSubmit = onSubmit,
            submitting = submitting, submissionError = submissionError,
            submitLabel = "Add user", helperText = "Create a user assigned to the selected company.",
        )
    }
}