package com.example.passwordmanager.ui.password.newElement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.domain.data.Password
import com.example.passwordmanager.domain.usecase.AddPasswordUseCase
import com.example.passwordmanager.domain.usecase.GeneratePasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewPasswordViewModel(
    private val generatePasswordUseCase: GeneratePasswordUseCase = GeneratePasswordUseCase(),
    private val addPasswordUseCase: AddPasswordUseCase = AddPasswordUseCase(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewPasswordUiState())
    val uiState: StateFlow<NewPasswordUiState> = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun updateName(name: String) {
        _uiState.update { currentState ->
            currentState.copy(
                buttonActive = name.isNotEmpty(),
                name = name
            )
        }
    }

    fun generatePassword() {
        _uiState.update { currentState ->
            currentState.copy(password = generatePasswordUseCase.invoke())
        }
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            addPasswordUseCase.invoke(
                password = Password(
                    name = uiState.value.name,
                    password = uiState.value.password
                )
            )
            _uiState.update { currentState ->
                currentState.copy(closeScreen = true)
            }
        }
    }

    private fun fetchData() {
        generatePassword()
    }
}