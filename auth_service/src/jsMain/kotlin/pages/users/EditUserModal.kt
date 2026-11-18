package org.example.pages.users

import androidx.compose.runtime.Composable
import com.auth_service.model.Company
import com.auth_service.model.CompanyUser

@Composable
fun EditUserModal(user: CompanyUser, company: Company, onClose: () -> Unit, onSubmit: (CompanyUser) -> Unit, submitting: Boolean = false, submissionError: String? = null) {
    UserModal("Edit ${user.username ?: user.fullName ?: "user"}", onClose = { if (!submitting) onClose() }) {
        UserForm(user = user, defaultCompany = company, onCancel = onClose, onSubmit = onSubmit,
            submitting = submitting, submissionError = submissionError,
            submitLabel = "Save changes", helperText = "Save this user and their company assignment.",
        )
    }
}