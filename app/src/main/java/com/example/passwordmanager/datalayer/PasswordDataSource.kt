package com.example.passwordmanager.datalayer

import com.example.passwordmanager.datalayer.db.AppRoomDatabase
import com.example.passwordmanager.datalayer.db.model.PasswordDao
import com.example.passwordmanager.datalayer.db.model.PasswordEntity
import kotlinx.coroutines.flow.Flow

class PasswordDataSource(
    private val dao: PasswordDao = AppRoomDatabase.getInstance().passwordDao()
) {
    fun insert(item: PasswordEntity) = dao.insert(item)

    fun getAll(): Flow<List<PasswordEntity>> = dao.getAll()

    fun get(id: Int): PasswordEntity? = dao.get(id)

    fun edit(item: PasswordEntity) = dao.update(item)

    fun delete(ids: List<Int>) = dao.delete(ids)
}
