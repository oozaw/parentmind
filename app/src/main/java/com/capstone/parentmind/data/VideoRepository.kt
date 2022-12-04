package com.capstone.parentmind.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArticleResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(
   private val apiService: ApiService
) {
   private val token: String = "Bearer 1|oWxyLexv9liDiJ59cZDPozv6f4SukAaLLDIABHcO"

   private val _videoResponse = MutableLiveData<ArticleResponse>()
   val videoResponse: LiveData<ArticleResponse> = _videoResponse

   fun getVideos(): LiveData<Result<ArticleResponse>> = liveData {
      emit(Result.Loading)
      try {
         val response = apiService.getVideo(token)
         _videoResponse.value = response
      } catch (e: Exception) {
         Log.d(TAG, "getVideos: ${e.message}")
         emit(Result.Error(e.message.toString()))
      }

      val data: LiveData<Result<ArticleResponse>> = videoResponse.map { Result.Success(it) }
      emitSource(data)
   }

   companion object {
      const val TAG = "VideoRepository"
   }
}