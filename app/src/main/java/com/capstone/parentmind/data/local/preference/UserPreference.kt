package com.capstone.parentmind.data.local.preference

import kotlinx.coroutines.flow.Flow

interface UserPreference {
   fun getToken(): Flow<String>

   fun getState(): Flow<Boolean>

   suspend fun login()

   suspend fun logout()
}