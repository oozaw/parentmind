package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.BuildConfig
import com.capstone.parentmind.data.local.preference.Preferences
import com.capstone.parentmind.view.article.ArtikelActivity
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class ServiceInterceptor : Interceptor {

//    private val token = Preferences(ArtikelActivity.context).getToken()
    private val token = BuildConfig.API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if(request.header("No-Authentication") == null){

            if (request.url.toString().contains("/register") == false) {
                request = token
                    .takeUnless { it.isNullOrEmpty() }
                    ?.let {
                        request.newBuilder()
                            .addHeader("Authorization", "Bearer $it")
                            .build()
                    }
                    ?: request
            }
        }
        if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        return chain.proceed(request)
    }
}