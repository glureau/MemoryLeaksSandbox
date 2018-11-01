package com.betclic.sandbox.memoryleakssandbox

import android.app.Application

class MemoryLeaksApplication : Application() {

    private var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}