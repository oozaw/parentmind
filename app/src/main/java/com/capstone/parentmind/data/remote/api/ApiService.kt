package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.data.remote.response.ArticleResponse
import com.capstone.parentmind.data.remote.response.BasicResponse
import com.capstone.parentmind.data.remote.response.LoginResponse
import retrofit2.http.*

interface ApiService {
   @GET("articles")
   suspend fun getAllArticles(
      @Header("Authorization") token: String,
      @Query("page") page: Int,
      @Query("size") size: Int
   ): ArticleResponse

   @GET("articles")
   suspend fun getArticlesType(
      @Header("Authorization") token: String,
      @Query("type") type: String,
      @Query("page") page: Int,
      @Query("size") size: Int
   ): ArticleResponse

   @GET("articles")
   suspend fun getSpecificArticle(
      @Header("Authorization") token: String,
      @Query("q") query: String,
      @Query("type") type: String,
      @Query("gender") gender: String,
      @Query("category") category: String,
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