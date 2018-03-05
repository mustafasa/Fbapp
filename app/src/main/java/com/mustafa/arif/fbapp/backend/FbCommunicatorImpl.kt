package com.mustafa.arif.fbapp.backend

import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.PostResponse
import retrofit2.Call
import javax.inject.Inject

class FbCommunicatorImpl @Inject constructor() : FbCommunicator {

    @Inject
    protected lateinit var facebookRetrofit: FacebookRetrofit

    override fun getFeed(limit: Int, access_token: String?, fields: String?): Call<FbResponse> {
        return facebookRetrofit.getFbFeed(limit, access_token, fields)
    }

    override fun getOlderFeed(url: String?): Call<FbResponse> {
        return facebookRetrofit.getOlderFeed(url)
    }

    override fun addPost(access_token: String, message: String?): Call<PostResponse> {
        return facebookRetrofit.addPost(access_token,message)
    }

}