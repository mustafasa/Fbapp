package com.mustafa.arif.fbapp.di

import android.content.Context
import com.mustafa.arif.fbapp.backend.*
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
class AppModule(context: Context) {

    private val BASE_URL = "https://graph.facebook.com/v2.12/"

    private var context: Context

    init {
        this.context = context
    }


    @Provides
    fun getContext(): Context {
        return context
    }

    @Provides
    fun getCommunicationChecker(communicationCheckerImpl: CommunicationCheckerImpl): CommunicationChecker {
        return communicationCheckerImpl
    }

    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun getFacebookRetrofit(retrofit: Retrofit): FacebookRetrofit {
        return retrofit.create<FacebookRetrofit>(FacebookRetrofit::class.java)
    }

    @Provides
    fun getFbCommunicator(fbCommunicatorImpl: FbCommunicatorImpl): FbCommunicator {
        return fbCommunicatorImpl
    }

}