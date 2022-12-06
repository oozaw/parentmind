package com.capstone.parentmind.view.article

import android.util.Log
import androidx.lifecycle.*
import com.capstone.parentmind.BuildConfig
import com.capstone.parentmind.data.AppExecutors
import com.capstone.parentmind.data.local.database.ArticleDao
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArtikelResponse
import com.capstone.parentmind.data.remote.response.DetailArtikelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.capstone.parentmind.utils.Result


class ArticleRepository private constructor(
    private val apiService : ApiService,
    private val articleDao: ArticleDao,
    private val appExecutors: AppExecutors
) {
    private val artikelResponse = MediatorLiveData<Result<ArtikelResponse>>()
    private val result = MediatorLiveData<Result<List<ArticleEntity>>>()
    private val detail = MediatorLiveData<Result<DetailArtikelResponse>>()

    fun getAllArticles() : LiveData<Result<List<ArticleEntity>>> {
        result.value = Result.Loading
        val client = apiService.getAllArticles("article")
        client.enqueue(object : Callback<ArtikelResponse> {
            override fun onResponse(
                call: Call<ArtikelResponse>,
                response: Response<ArtikelResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    Log.d(TAG, "onResponse: $articles")
                    val articlesList = ArrayList<ArticleEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { item ->
                            val isBookmarked = articleDao.isArticleBookmarked(item.id)
                            val article = ArticleEntity(
                                item.id,
                                item.thumbnail,
                                item.title,
                                item.link,
                                isBookmarked
                            )
                            articlesList.add(article)
                        }
                        articleDao.deleteAll()
                        articleDao.insertArticle(articlesList)
                    }
                }else{
                    result.value = Result.Error(response.message())
                    Log.d(TAG, "onResponseError: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArtikelResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })

        val localData = articleDao.getAllArticles()
        result.addSource(localData) { newData: List<ArticleEntity> ->
            result.value = Result.Success(newData)
        }
        Log.d(TAG, "getAllArticles: ${localData.value}")
        return result
    }

    fun getDetailArticles(id: Int) : LiveData<Result<DetailArtikelResponse>> {
        detail.value = Result.Loading
        val detailResponse = MutableLiveData<DetailArtikelResponse>()
        val client = apiService.getDetailArticle(id)
        client.enqueue(object : Callback<DetailArtikelResponse> {
            override fun onResponse(
                call: Call<DetailArtikelResponse>,
                response: Response<DetailArtikelResponse>
            ) {
                if (response.isSuccessful){
                    detailResponse.value = response.body()
                }else{
                    detail.value = Result.Error(response.message())
                }
            }

            override fun onFailure(call: Call<DetailArtikelResponse>, t: Throwable) {
                detail.value = Result.Error(t.message.toString())
            }
        })
        detail.addSource(detailResponse){data : DetailArtikelResponse ->
            detail.value = Result.Success(data)
        }
        return detail
    }

    fun setBookmarkedArticle(article: ArticleEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            article.bookmark = bookmarkState
            articleDao.updateArticle(article)
        }
    }

    fun checkSave(id: Int) : Boolean{
        return articleDao.isArticleBookmarked(id)
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            apiService: ApiService,
            articleDao: ArticleDao,
            appExecutors: AppExecutors
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService, articleDao, appExecutors)
            }.also { instance = it }

        private const val TAG = "ArticleRepository"
    }
}