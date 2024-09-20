package com.example.passwordmanager.datalayer.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.passwordmanager.datalayer.db.model.PasswordDao
import com.example.passwordmanager.datalayer.db.model.PasswordEntity

@Database(
    entities = [
        PasswordEntity::class,
    ],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun passwordDao(): PasswordDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(): AppRoomDatabase {
            INSTANCE.let {
                if (it == null) {
                    throw RuntimeException("DB is not initialized")
                } else {
                    return it
                }
            }
        }

        fun init(context: Context): AppRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AppRoomDatabase::class.java, "password_manager")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
