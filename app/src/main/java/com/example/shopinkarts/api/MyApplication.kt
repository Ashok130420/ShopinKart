package com.example.shopinkarts.api


import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "dd973c9e-182d-4405-8b7e-7d3bd48a05cd"

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Logging set to help debug issues, remove before releasing your app.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        instance = this
    }

    companion object {
        lateinit var instance: MyApplication
            private set
    }
}