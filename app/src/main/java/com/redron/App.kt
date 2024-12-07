package com.redron

import android.app.Application
import com.redron.data.db.JokesDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        JokesDatabase.initDatabase(this)
    }
}