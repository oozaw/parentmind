package com.capstone.parentmind.module

import com.capstone.parentmind.BuildConfig
import com.capstone.parentmind.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
   companion object {
      private const val BASE_URL = BuildConfig.BASE_URL

      @Provides
      @Singleton
      fun provideApiService(): ApiService {
         val loggingInterceptor = if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
         else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

         val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
         val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

         return retrofit.create(ApiService::class.java)
      }
   }
}