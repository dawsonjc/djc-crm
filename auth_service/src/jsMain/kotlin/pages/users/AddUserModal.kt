package org.example.pages.users

import androidx.compose.runtime.Composable
import com.auth_service.model.Company
import com.auth_service.model.CompanyUser

@Composable
fun AddUserModal(company: Company, onClose: () -> Unit, onSubmit: (CompanyUser) -> Unit) {
    UserModal("Add user", onClose) {
        UserForm(user = null, defaultCompany = company, onCancel = onClose, onSubmit = onSubmit)
    }
}