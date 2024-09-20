package com.example.passwordmanager.ui.password.list

import com.example.passwordmanager.domain.data.Password

data class PasswordsListUiState(
    val list: List<Password> = emptyList(),
    val deleteMode: Boolean = false,
    val idItemToDelete: Password? = null,
)
