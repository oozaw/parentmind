package com.capstone.parentmind.data.local.preference

import com.capstone.parentmind.data.remote.response.User
import kotlinx.coroutines.flow.Flow

interface AuthPreference {
   fun getToken(): String

   fun getState(): Flow<Boolean>

   fun getUser(): Flow<User>

   suspend fun login(user: User)

   suspend fun logout()
}