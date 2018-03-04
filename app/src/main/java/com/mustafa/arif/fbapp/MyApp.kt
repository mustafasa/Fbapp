package com.mustafa.arif.fbapp

import android.app.Application
import com.facebook.FacebookSdk
import com.mustafa.arif.fbapp.di.AppComponent
import com.mustafa.arif.fbapp.di.AppModule
import com.mustafa.arif.fbapp.di.DaggerAppComponent


class MyApp : Application() {
    var appComponent: AppComponent?= null

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this)).build()
        FacebookSdk.sdkInitialize(applicationContext)
    }

    fun getActivityComponent(): AppComponent? {
        return appComponent
    }
}