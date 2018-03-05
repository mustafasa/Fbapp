package com.mustafa.arif.fbapp.backend

import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.PostResponse
import org.jetbrains.annotations.NotNull
import retrofit2.Call

/**
 * Class to establish network request to fb API
 */
interface FbCommunicator {

    /**
     * Method to call facebook web api for feeds
     * @param limit number of post per request
     * @param access_token session id
     * @param fields  fields that are needed in response
     *
     * @return FbResponse object
     */
    @NotNull
    fun getFeed(limit: Int, access_token: String?, fields: String?): Call<FbResponse>

    /**
     * Method to call facebook web api for older feeds
     * @param url open new url for next feed
     *
     * @return FbResponse object
     */
    @NotNull
    fun getOlderFeed(url: String?): Call<FbResponse>

    /**
     * Method to call facebook web api for older feeds
     * @param access_token session id
     * @param message the message to post on facebook wall
     *
     * @return PostResponse object
     */
    @NotNull
    fun addPost(access_token: String, message: String?): Call<PostResponse>

}
