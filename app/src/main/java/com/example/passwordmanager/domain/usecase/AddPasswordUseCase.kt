package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.datalayer.PasswordRepository
import com.example.passwordmanager.domain.converter.toDb
import com.example.passwordmanager.domain.data.Password

class AddPasswordUseCase(
    private val repository: PasswordRepository = PasswordRepository()
) {
    operator fun invoke(password: Password): Long {
        return try {
            repository.insert(password.toDb())
        } catch (e: Exception) {
            -1
        }
    }
}
