package com.capstone.parentmind.data

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.capstone.parentmind.data.local.database.ArticleDao
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.data.local.preference.AuthPreference
import com.capstone.parentmind.data.local.preference.UserPreference
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArticleResponse
import com.capstone.parentmind.data.remote.response.ArticlesItem
import com.capstone.parentmind.data.remote.response.DetailArtikelResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.capstone.parentmind.data.Result
import javax.inject.Inject


class ArticleRepository @Inject constructor(
    private val apiService : ApiService,
    userPreference: AuthPreference,
    private val articleDao: ArticleDao
) {

    private val token = userPreference.getToken()

    private val _articleResponse = MutableLiveData<ArticleResponse>()
    val articleResponse: LiveData<ArticleResponse> = _articleResponse

    fun getArticle(type: String): LiveData<Result<ArticleResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getArticlesType(token, type)
            Log.d(TAG, "getArticle: $token")
            _articleResponse.value = response
        } catch (e: Exception) {
            Log.d(TAG, "getArticle: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }

        val data: LiveData<Result<ArticleResponse>> = articleResponse.map { Result.Success(it) }
        emitSource(data)
    }

    fun getAllTypeArticlePaging(): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, token, "")
            }
        ).liveData
    }

    fun getArticlePaging(type: String): LiveData<PagingData<ArticlesItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                ArticlePagingSource(apiService, token, type)
            }
        ).liveData
    }

    companion object {
        const val TAG = "VideoRepository"
    }

//    private val articleResponse = MediatorLiveData<Result<ArticleResponse>>()
//    private val result = MediatorLiveData<Result<List<ArticleEntity>>>()
//    private val detail = MediatorLiveData<Result<DetailArtikelResponse>>()

//    fun getAllArticles() : LiveData<Result<List<ArticleEntity>>> = liveData {
//        emit(Result.Loading)
//        val client = apiService.getAllArticles("article")
//        client.enqueue(object : Callback<ArticleResponse> {
//            override fun onResponse(
//                call: Call<ArticleResponse>,
//                response: Response<ArticleResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val articles = response.body()?.articles
//                    Log.d(TAG, "onResponse: $articles")
//                    val articlesList = ArrayList<ArticleEntity>()
//                    appExecutors.diskIO.execute {
//                        articles?.forEach { item ->
//                            val isBookmarked = articleDao.isArticleBookmarked(item.id)
//                            val article = ArticleEntity(
//                                item.id,
//                                item.thumbnail,
//                                item.title,
//                                item.link,
//                                isBookmarked
//                            )
//                            articlesList.add(article)
//                        }
//                        articleDao.deleteAll()
//                        articleDao.insertArticle(articlesList)
//                    }
//                }else{
//                    result.value = Result.Error(response.sta .message())
//                    Log.d(TAG, "onResponseError: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
//                result.value = Result.Error(t.message.toString())
//            }
//        })
//
//        val localData = articleDao.getAllArticles()
//        result.addSource(localData) { newData: List<ArticleEntity> ->
//            result.value = Result.Success(newData)
//        }
//        Log.d(TAG, "getAllArticles: ${localData.value}")
//        return result
//    }
//
//    fun getDetailArticles(id: Int) : LiveData<Result<DetailArtikelResponse>> {
//        detail.value = Result.Loading
//        val detailResponse = MutableLiveData<DetailArtikelResponse>()
//        val client = apiService.getDetailArticle(id)
//        client.enqueue(object : Callback<DetailArtikelResponse> {
//            override fun onResponse(
//                call: Call<DetailArtikelResponse>,
//                response: Response<DetailArtikelResponse>
//            ) {
//                if (response.isSuccessful){
//                    detailResponse.value = response.body()
//                }else{
//                    detail.value = Result.Error(response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<DetailArtikelResponse>, t: Throwable) {
//                detail.value = Result.Error(t.message.toString())
//            }
//        })
//        detail.addSource(detailResponse){data : DetailArtikelResponse ->
//            detail.value = Result.Success(data)
//        }
//        return detail
//    }
//
//    fun setBookmarkedArticle(article: ArticleEntity, bookmarkState: Boolean) {
//        appExecutors.diskIO.execute {
//            article.bookmark = bookmarkState
//            articleDao.updateArticle(article)
//        }
//    }
//
//    fun checkSave(id: Int) : Boolean{
//        return articleDao.isArticleBookmarked(id)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: ArticleRepository? = null
//        fun getInstance(
//            apiService: ApiService,
//            articleDao: ArticleDao,
//            appExecutors: AppExecutors
//        ): ArticleRepository =
//            instance ?: synchronized(this) {
//                instance ?: ArticleRepository(apiService, articleDao, appExecutors)
//            }.also { instance = it }
//
//        private const val TAG = "ArticleRepository"
//    }
}