package com.example.passwordmanager.datalayer

import com.example.passwordmanager.datalayer.db.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

class PasswordRepository(
    private val localDataSource: PasswordDataSource = PasswordDataSource()
) {
    fun insert(password: PasswordEntity) = localDataSource.insert(password)

    fun getAll(): Flow<List<PasswordEntity>> = localDataSource.getAll()

    fun get(id: Int) = localDataSource.get(id)

    fun edit(password: PasswordEntity) = localDataSource.edit(password)

    fun delete(ids: List<Int>) = localDataSource.delete(ids)
}
