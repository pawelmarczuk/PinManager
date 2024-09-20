package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.datalayer.PasswordRepository
import com.example.passwordmanager.domain.data.Password

class DeletePasswordUseCase(
    private val repository: PasswordRepository = PasswordRepository()
) {
    operator fun invoke(passwords: List<Password>) = repository.delete(passwords.map { it.id })
}
