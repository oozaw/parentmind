package com.capstone.parentmind.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.capstone.parentmind.data.local.preference.AuthPreference
import com.capstone.parentmind.data.local.preference.UserPreference
import com.capstone.parentmind.data.remote.api.ApiService
import com.capstone.parentmind.data.remote.response.BasicResponse
import com.capstone.parentmind.data.remote.response.LoginResponse
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
   private val apiService: ApiService,
   private val userPreference: AuthPreference
) {
   private val _loginResponse = MutableLiveData<LoginResponse>()
   val loginResponse: LiveData<LoginResponse> = _loginResponse

   private var _registerResponse = MutableLiveData<BasicResponse>()
   val registerResponse: LiveData<BasicResponse> = _registerResponse

   fun checkStateLogin() = userPreference.getState()

   fun getUser() = userPreference.getUser()

   suspend fun logout() = userPreference.logout()

   fun login(email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
      emit(Result.Loading)
      try {
         val response = apiService.login(email, password)
         _loginResponse.value = response
         if (response.status) userPreference.login(response.user)
      } catch (e: Exception) {
         Log.e(TAG, "login: ${e.message}")
         emit(Result.Error(e.message.toString()))
      }

      val data: LiveData<Result<LoginResponse>> = loginResponse.map { Result.Success(it) }
      emitSource(data)
   }

   fun loginFB(name: String, email: String, password: String): LiveData<Result<LoginResponse>> = liveData {
      emit(Result.Loading)
      try {
         val response = apiService.loginFB(name, email, password)
         _loginResponse.value = response
         if (response.status) userPreference.login(response.user)
      } catch (e: Exception) {
         Log.e(TAG, "login: ${e.message}")
         emit(Result.Error(e.message.toString()))
      }

      val data: LiveData<Result<LoginResponse>> = loginResponse.map { Result.Success(it) }
      emitSource(data)
   }

   fun register(name: String, username: String, email: String, password: String): LiveData<Result<BasicResponse>> = liveData {
      emit(Result.Loading)
      try {
         val response = apiService.register(name, username, email, password)
         _registerResponse.value = response
      } catch (e: Exception) {
         Log.e(TAG, "register: ${e.message}")
         emit(Result.Error(e.message.toString()))
      }

      val data: LiveData<Result<BasicResponse>> = registerResponse.map { Result.Success(it) }
      emitSource(data)
   }

   companion object {
      const val TAG = "UserRepository"
   }
}