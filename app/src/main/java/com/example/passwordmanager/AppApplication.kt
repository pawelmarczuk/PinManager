package com.example.passwordmanager

import android.app.Application
import com.example.passwordmanager.datalayer.db.AppRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AppApplication : Application() {
    private val job = Job()
    private val ioScope = CoroutineScope(Dispatchers.IO + job)

    override fun onCreate() {
        super.onCreate()
        initModules()
    }

    override fun onTerminate() {
        job.cancel()
        super.onTerminate()
    }

    private fun initModules() {
        ioScope.launch {
            initDatabase()
        }
    }

    private fun initDatabase() {
        AppRoomDatabase.init(this)
    }
}
