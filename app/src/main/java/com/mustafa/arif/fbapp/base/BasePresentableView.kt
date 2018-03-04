package com.mustafa.arif.fbapp.base

import android.support.annotation.StringRes


interface BasePresentableView  {
     fun toastMessage(@StringRes message: Int)

}