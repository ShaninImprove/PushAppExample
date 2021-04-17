package com.androidschool.app.service

import com.androidschool.app.data.PushExampleApiService
import com.androidschool.app.di.component.AppComponent
import com.androidschool.app.di.scopes.FirebaseServiceScope
import dagger.Component
import dagger.android.AndroidInjector

@Component(dependencies = [AppComponent::class])
@FirebaseServiceScope
interface FirebaseServiceComponent : AndroidInjector<ExampleFirebaseService> {

    override fun inject(instance: ExampleFirebaseService?)
}