package com.capstone.parentmind.data

import android.util.Log
import androidx.lifecycle.*
import com.capstone.parentmind.data.local.preference.AuthPreference
import com.capstone.parentmind.data.local.preference.UserPreference
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.ArticleResponse
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoRepository @Inject constructor(
   private val apiService: ApiService,
   private val userPreference: AuthPreference
) {
   private val token = userPreference.getToken()

   private val _videoResponse = MutableLiveData<ArticleResponse>()
   val videoResponse: LiveData<ArticleResponse> = _videoResponse

   fun getVideos(): LiveData<Result<ArticleResponse>> = liveData {
      emit(Result.Loading)
      try {
         val response = apiService.getVideo(token)
         Log.d(TAG, "getVideos: $token")
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