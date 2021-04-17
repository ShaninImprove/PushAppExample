package com.androidschool.app.di

import com.androidschool.app.PushApplication
import com.androidschool.app.di.component.AppComponent
import com.androidschool.app.di.component.DaggerAppComponent
import com.androidschool.app.service.DaggerFirebaseServiceComponent
import com.androidschool.app.service.FirebaseServiceComponent

class Components constructor(private val application: PushApplication) {

    private var appComponent: AppComponent? = null

    fun getAppComponent(): AppComponent {
        if (appComponent == null) {
            this.appComponent = DaggerAppComponent.factory().create(application)
        }
        return appComponent!!
    }

    fun clearAppComponent() {
        appComponent = null
    }

    private var firebaseServiceComponent: FirebaseServiceComponent? = null

    fun getFirebaseServiceComponent(): FirebaseServiceComponent {
        if (firebaseServiceComponent == null) {
            firebaseServiceComponent = DaggerFirebaseServiceComponent
                .builder()
                .appComponent(appComponent)
                .build()
        }
        return firebaseServiceComponent!!
    }

    fun clearFirebaseServiceComponent() {
        firebaseServiceComponent = null
    }
}