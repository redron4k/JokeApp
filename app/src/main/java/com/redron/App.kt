package com.redron

import android.app.Application
import com.redron.data.datasource.local.JokesDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        JokesDatabase.initDatabase(this)
    }
}