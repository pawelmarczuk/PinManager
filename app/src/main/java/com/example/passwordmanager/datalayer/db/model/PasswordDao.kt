package com.example.passwordmanager.datalayer.db.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert
    fun insert(destination: PasswordEntity): Long

    @Query("SELECT * FROM passwords ORDER BY name ASC")
    fun getAll(): Flow<List<PasswordEntity>>

    @Query("SELECT * FROM passwords WHERE id = :id")
    fun get(id: Int): PasswordEntity?

    @Update
    fun update(destination: PasswordEntity)

    @Query("DELETE FROM passwords WHERE id in (:ids)")
    fun delete(ids: List<Int>)
}
