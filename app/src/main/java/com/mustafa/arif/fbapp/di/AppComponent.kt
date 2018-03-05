package com.mustafa.arif.fbapp.di

import dagger.Component

@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    /**
     * Create UI SubComponent
     * @return SubComponent on all the activity(@link SubComponent)
     */
    fun getSubComponent():SubComponent
}