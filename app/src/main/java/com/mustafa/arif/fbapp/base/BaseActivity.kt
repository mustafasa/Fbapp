package com.mustafa.arif.fbapp.base

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import javax.inject.Inject


/**
 * Created by arifm2 on 3/2/2018.
 */
abstract class BaseActivity<V : BasePresentableView, P : BasePresenter<V>> : AppCompatActivity(), BasePresentableView {

    @Inject
    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureViewElements(savedInstanceState)
    }

    /**
     * The method to configureViewElements specific controls.
     */
    protected abstract fun configureViewElements(savedInstanceState: Bundle?)


    override fun toastMessage(@StringRes message: Int) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, message, duration)
        toast.show()
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.unbind()
    }


}

