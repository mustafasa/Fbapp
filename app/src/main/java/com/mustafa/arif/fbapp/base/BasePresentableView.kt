package com.mustafa.arif.fbapp.base

import android.support.annotation.StringRes




interface BasePresentableView  {
     fun toastMessage(@StringRes message: Int)
     /**
      * Open a default browser app with provided uri.
      *
      * @param url the url address to open.
      */
     fun openBrowser(url: String)

}