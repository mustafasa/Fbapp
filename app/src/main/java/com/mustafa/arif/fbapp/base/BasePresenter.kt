package com.mustafa.arif.fbapp.base

import android.support.annotation.NonNull


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


}