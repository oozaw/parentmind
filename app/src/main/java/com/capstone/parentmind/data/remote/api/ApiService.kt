package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.data.remote.response.ArticleResponse
import com.capstone.parentmind.data.remote.response.BasicResponse
import com.capstone.parentmind.data.remote.response.LoginResponse
import retrofit2.http.*

interface ApiService {
   @GET("articles")
   suspend fun getAllArticles(
      @Header("Authorization") token: String
   ): ArticleResponse

   @GET("articles")
   suspend fun getAllArticlesPage(
      @Header("Authorization") token: String,
      @Query("page") page: Int,
      @Query("size") size: Int
   ): ArticleResponse

   @GET("articles/{id}")
   suspend fun getDetailArticle(
      @Path("id") id : Int
   ): ArticleResponse

   @GET("articles")
   suspend fun getArticlesType(
      @Header("Authorization") token: String,
      @Query("type") type: String
   ): ArticleResponse

   @GET("articles")
   suspend fun getArticlesTypePage(
      @Header("Authorization") token: String,
      @Query("type") type: String,
      @Query("page") page: Int,
      @Query("size") size: Int
   ): ArticleResponse

   @GET("articles?type=video")
   suspend fun getVideo(
      @Header("Authorization") token: String
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