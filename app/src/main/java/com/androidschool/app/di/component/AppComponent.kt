package com.androidschool.app.di.component

import android.content.Context
import com.androidschool.app.PushApplication
import com.androidschool.app.data.PushExampleApiService
import com.androidschool.app.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class
])
@Singleton
interface AppComponent : AndroidInjector<PushApplication> {

    override fun inject(instance: PushApplication)

    fun apiService(): PushExampleApiService

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            @Named("ApplicationContext")
            context: Context): AppComponent
    }
}