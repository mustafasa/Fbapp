package com.mustafa.arif.fbapp.backend

import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.PostResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

/**
 * Retrofit interface, which communicate with facebook web api/Sdk
 */
interface FacebookRetrofit {

    /**
     * Get inital facebook home feeds of currently logged in user.
     * @param limit number of post per request
     * @param access_token session id
     * @param fields  fields that are needed in response
     *
     * @return FbResponse object
     */
    @GET("/me/feed/")
    fun getFbFeed(
            @Query("limit") limit: Int,
            @Query("access_token") access_token: String?,
            @Query("fields") picture: String?
    )
            : Call<FbResponse>

    /**
     * Get older facebook home feeds of currently logged in user.
     * @param url open new url for next feed
     *
     * @return FbResponse object
     */
    @GET
    fun getOlderFeed(@Url url: String?): Call<FbResponse>

    /**
     * Add post on currently logged in user.
     * @param access_token session id
     * @param message the message to post on facebook wall
     *
     * @return FbResponse object
     */
    @POST("/me/feed/")
    fun addPost(@Query("access_token") access_token: String?, @Query("message") message:
    String?): Call<PostResponse>
}