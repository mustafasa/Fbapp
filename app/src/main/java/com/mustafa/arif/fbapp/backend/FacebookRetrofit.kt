package com.mustafa.arif.fbapp.backend

import com.mustafa.arif.fbapp.backend.model.FbResponse
import com.mustafa.arif.fbapp.backend.model.PostResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface FacebookRetrofit {

    @GET("/me/feed/")
    fun getFbFeed(
    @Query("limit") limit: Int,
    @Query("access_token")access_token: String?,
    @Query("fields") picture: String?
    )
    : Call<FbResponse>

    @GET
    fun getOlderFeed(@Url url: String?): Call<FbResponse>

    @POST("/me/feed/")
    fun addPost(@Query("access_token")access_token: String?, @Query("message") message: String?): Call<PostResponse>

}