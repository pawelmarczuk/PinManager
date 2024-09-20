package com.example.passwordmanager.ui.password.newElement

data class NewPasswordUiState(
    val name: String = "",
    val password: String = "",
    val buttonActive: Boolean = false,
    val closeScreen: Boolean = false,
)
