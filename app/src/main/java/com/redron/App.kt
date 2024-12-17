package com.redron

import android.app.Application
import com.redron.data.datasource.local.JokesDatabase
import com.redron.di.AppComponent
import com.redron.di.DaggerAppComponent


class App : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
        JokesDatabase.initDatabase(this)
    }
}