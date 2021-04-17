package com.androidschool.app

import android.app.Application
import com.androidschool.app.di.Components
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class PushApplication : Application() {

    lateinit var components: Components

    override fun onCreate() {
        super.onCreate()
        instance = this
        initFcm()
        initDagger()
    }

    private fun initFcm() {
        Firebase.messaging.subscribeToTopic("alert")
    }

    private fun initDagger() {
        components = Components(this)
        components.getAppComponent().inject(this)
    }

    override fun onTerminate() {
        components.clearAppComponent()
        super.onTerminate()
    }

    companion object {
        lateinit var instance: PushApplication
    }
}