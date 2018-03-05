package com.mustafa.arif.fbapp.di

import com.mustafa.arif.fbapp.ui.HomeActivity
import dagger.Subcomponent


@Subcomponent
interface SubComponent {
    /**
     * Provide mapping inject for HomeActivity
     * @param activity provide HomeActivity
     */
    fun inject(activity: HomeActivity)
}