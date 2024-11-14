package com.junenine.crashcatcher

import android.app.Application

class Application : Application() {
    private val crashHandler = CrashHandler.initialize(this)

    override fun onCreate() {
        super.onCreate()
        crashHandler.setRestartActivity(
            RestartActivity()
        )
    }
}