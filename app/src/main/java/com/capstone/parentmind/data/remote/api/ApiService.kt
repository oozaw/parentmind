package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.data.remote.response.ArticleResponse
import com.capstone.parentmind.data.remote.response.BasicResponse
import com.capstone.parentmind.data.remote.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
   @GET("articles")
   suspend fun getArticle(
      @Header("Authorization") token: String
   ): ArticleResponse

   @GET("articles?type=video")
   suspend fun getVideo(
      @Header("Authorization") token: String
   ): ArticleResponse

   @GET("articles?type=video")
   suspend fun getVideoPage(
      @Header("Authorization") token: String,
      @Query("page") page: Int,
      @Query("size") size: Int
   ): ArticleResponse

   @POST("login")
   @FormUrlEncoded
   suspend fun login(
      @Field("email") email: String,
      @Field("password") password: String
   ): LoginResponse

   @POST("login-fb")
   @FormUrlEncoded
   suspend fun loginFB(
      @Field("name") name: String,
      @Field("email") email: String,
      @Field("password") password: String
   ): LoginResponse

   @POST("register")
   @FormUrlEncoded
   suspend fun register(
      @Field("name") name: String,
      @Field("username") username: String,
      @Field("email") email: String,
      @Field("password") password: String
   ): BasicResponse
}