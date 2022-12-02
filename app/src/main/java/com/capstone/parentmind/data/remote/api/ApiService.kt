package com.capstone.parentmind.data.remote.api

import com.capstone.parentmind.data.remote.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
   @GET("articles")
   suspend fun getArticle(
      @Header("Authorization") token: String
   ): ArticleResponse
}