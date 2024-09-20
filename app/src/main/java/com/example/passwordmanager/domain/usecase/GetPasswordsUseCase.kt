package com.example.passwordmanager.domain.usecase

import com.example.passwordmanager.datalayer.PasswordRepository
import com.example.passwordmanager.domain.converter.toDomain
import com.example.passwordmanager.domain.data.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class GetPasswordsUseCase(
    private val repository: PasswordRepository = PasswordRepository()
) {
    operator fun invoke(): Flow<List<Password>> {
        return try {
            return repository.getAll().map {
                it.map {
                    it.toDomain()
                }
            }
        } catch (e: Exception) {
            emptyFlow()
        }
    }
}
