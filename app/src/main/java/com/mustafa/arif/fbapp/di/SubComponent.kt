package com.mustafa.arif.fbapp.di

import com.mustafa.arif.fbapp.ui.HomeActivity
import dagger.Subcomponent


@Subcomponent
interface SubComponent {
    fun inject(activity: HomeActivity)
}