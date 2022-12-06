package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.BuildConfig
import com.capstone.parentmind.data.remote.response.ArticleResponse
import com.capstone.parentmind.data.remote.response.ArtikelResponse
import com.capstone.parentmind.data.remote.response.DetailArtikelResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
   @GET("articles")
   suspend fun getArticle(
      @Header("Authorization") token: String
   ): ArticleResponse

   @GET("articles")
   fun getAllArticles(
      @Query("type") type : String
   ): Call<ArtikelResponse>

   @GET("articles/{id}")
   fun getDetailArticle(
      @Path("id") id : Int
   ): Call<DetailArtikelResponse>
}