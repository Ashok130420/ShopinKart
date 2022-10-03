package com.example.shopinkarts.api


import android.app.Application
import com.onesignal.OneSignal

const val ONESIGNAL_APP_ID = "50e8d4b7-8f07-4502-9430-9d3d68bc73ac"

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