package com.mustafa.arif.fbapp.di

import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun getSubComponent():SubComponent
}