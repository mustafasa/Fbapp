package com.mustafa.arif.fbapp.backend

import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.PostResponse
import retrofit2.Call


interface FbCommunicator {

    /**
     * Method to call facebook web api for feeds
     * @return
     */
    fun getFeed(limit: Int, access_token: String?, fields: String?): Call<FbResponse>

    /**
     * Method to call facebook web api for older feeds
     * @return
     */
    fun getOlderFeed(url: String?): Call<FbResponse>

    /**
     * Method to call facebook web api for older feeds
     * @return
     */
    fun addPost(access_token: String, message: String?): Call<PostResponse>


}
