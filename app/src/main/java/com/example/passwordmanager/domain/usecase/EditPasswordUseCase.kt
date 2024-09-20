package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.datalayer.PasswordRepository
import com.example.passwordmanager.domain.converter.toDb
import com.example.passwordmanager.domain.data.Password

class EditPasswordUseCase(
    private val repository: PasswordRepository = PasswordRepository()
) {
    operator fun invoke(password: Password)  = repository.edit(password.toDb())
}
