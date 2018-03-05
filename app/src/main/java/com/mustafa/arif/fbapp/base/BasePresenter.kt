package com.mustafa.arif.fbapp.base

import android.support.annotation.NonNull
import java.net.URL


abstract class BasePresenter<V : BasePresentableView> {

    protected var view: V? = null

    /**
     * Binding of the Presenter to its View. After the Presenter is bound to a view, it is free to
     * make calls to the view, including directly from withing this method.
     *
     * @param view A view to bind to.
     */
    fun bind(@NonNull view: V) {
        this.view = view
    }

    /**
     * Unbinding of the Presenter from a View it was previously bound to.
     */
    fun unbind() {
        view = null
    }

    /**
     * validate url
     * @param url string url to open
     *
     * @return if valid true, else  false
     **/
    fun isValid(url: String): Boolean {
        /* Try creating a valid URL */
        try {
            URL(url).toURI()
            return true
        } catch (e: Exception) {
            return false
        }
    }


}