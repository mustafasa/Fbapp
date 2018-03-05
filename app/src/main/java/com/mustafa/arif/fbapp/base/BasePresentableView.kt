package com.mustafa.arif.fbapp.base

import android.support.annotation.StringRes




interface BasePresentableView  {
    /**
     * show standard toast message
     * @param StringId to show on toast
     */
     fun toastMessage(@StringRes message: Int)
     /**
      * Open a default browser app with provided uri.
      *
      * @param url the url address to open.
      */
     fun openBrowser(url: String)

}