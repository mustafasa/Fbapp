package com.mustafa.arif.fbapp.ui

import android.os.Bundle
import com.mustafa.arif.fbapp.R
import com.mustafa.arif.fbapp.base.BaseActivity

class SplashScreen : BaseActivity<SplashScreenPresenter.View, SplashScreenPresenter>(),
        SplashScreenPresenter.View {

    override fun configureViewElements(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_splash_screen)
    }
}
