package com.capstone.parentmind.data

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.parentmind.data.local.database.ArticleDao
import com.capstone.parentmind.data.local.preference.AuthPreference
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArticlesItem
import javax.inject.Inject


class ArticleRepository @Inject constructor(
    private val apiService : ApiService,
    userPreference: AuthPreference,
    private val articleDao: ArticleDao
) {

    private val token = userPreference.getToken()

    fun getAllTypeArticle(): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, token)
            }
        ).liveData
    }

    fun getArticle(type: String): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, token, type = type)
            }
        ).liveData
    }

    fun getSpecificArticle(query: String, type: String, gender: String, category: String): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                ArticlePagingSource(
                    apiService,
                    token,
                    gender = gender,
                    query = query,
                    type = type,
                    category = category
                )
            }
        ).liveData
    }

    companion object {
        const val TAG = "VideoRepository"
    }
}