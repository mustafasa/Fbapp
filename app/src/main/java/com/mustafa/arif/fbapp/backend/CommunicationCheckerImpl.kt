package com.mustafa.arif.fbapp.backend

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

import javax.inject.Inject



class CommunicationCheckerImpl @Inject
constructor(private val context: Context) : CommunicationChecker {

    override val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
