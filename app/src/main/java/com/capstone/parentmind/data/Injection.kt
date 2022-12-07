package com.capstone.parentmind.data

import android.content.Context
import com.capstone.parentmind.data.local.database.ArticleDatabase
import com.capstone.parentmind.data.remote.api.ApiConfig
import com.capstone.parentmind.view.article.ArticleRepository

object Injection {
    fun provideRepository(context: Context): ArticleRepository {
        val database = ArticleDatabase.getDatabase(context)
        val dao = database.articleDao()
        val apiService = ApiConfig.getApiService()
        val appExecutors = AppExecutors()
        return ArticleRepository.getInstance(apiService, dao, appExecutors)
    }
}