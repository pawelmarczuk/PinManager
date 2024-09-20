package com.example.passwordmanager.ui.password.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.domain.data.Password
import com.example.passwordmanager.domain.usecase.DeletePasswordUseCase
import com.example.passwordmanager.domain.usecase.GetPasswordsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PasswordsListViewModel(
    private val getPasswordsUseCase: GetPasswordsUseCase = GetPasswordsUseCase(),
    private val deletePasswordUseCase: DeletePasswordUseCase = DeletePasswordUseCase(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(PasswordsListUiState())
    val uiState: StateFlow<PasswordsListUiState> = _uiState.asStateFlow()

    fun loadData() {
        viewModelScope.launch {
            val list = getPasswordsUseCase.invoke().first()
            _uiState.update { currentState ->
                currentState.copy(list = list)
            }
        }
    }

    fun turnOnDeleteOption() {
        _uiState.update { currentState ->
            currentState.copy(deleteMode = true)
        }
    }

    fun turnOffDeleteOption() {
        _uiState.update { currentState ->
            currentState.copy(deleteMode = false)
        }
    }

    fun showDialogForRemovePassword(pass: Password) {
        _uiState.update { currentState ->
            currentState.copy(idItemToDelete = pass)
        }
    }

    fun skipRemovePassword() {
        _uiState.update { currentState ->
            currentState.copy(idItemToDelete = null)
        }
    }

    fun removePassword(pass: Password) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordUseCase.invoke(arrayListOf(pass))
            skipRemovePassword()
            loadData()
        }
    }
}
