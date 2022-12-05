package com.capstone.parentmind.data.local.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.capstone.parentmind.data.remote.response.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserPreference constructor(private val dataStore: DataStore<Preferences>): AuthPreference {
//   val token: String = runBlocking { dataStore.data.first()[TOKEN_KEY] ?: "" }

   override fun getToken(): String = runBlocking { dataStore.data.first()[TOKEN_KEY] ?: "" }

   override fun getState(): Flow<Boolean> {
      return dataStore.data.map { pref ->
         pref[STATE_KEY] ?: false
      }
   }

   override fun getUser(): Flow<User> {
      return  dataStore.data.map { pref ->
         User(
            id = pref[ID_KEY] ?: "",
            name = pref[NAME_KEY] ?: "",
            username = pref[USERNAME_KEY] ?: "",
            email = pref[EMAIL_KEY] ?: "",
            token = pref[TOKEN_KEY] ?: "",
         )
      }
   }

   override suspend fun login(user: User) {
      dataStore.edit { pref->
         pref[ID_KEY] = user.id
         pref[NAME_KEY] = user.name
         pref[USERNAME_KEY] = user.username
         pref[EMAIL_KEY] = user.email
         pref[TOKEN_KEY] = "Bearer ${user.token}"
         pref[STATE_KEY] = true
      }
   }

   override suspend fun logout() {
      dataStore.edit { pref ->
         pref[ID_KEY] = ""
         pref[NAME_KEY] = ""
         pref[USERNAME_KEY] = ""
         pref[EMAIL_KEY] = ""
         pref[TOKEN_KEY] = ""
         pref[STATE_KEY] = false
      }
   }

   companion object {
      private val ID_KEY = stringPreferencesKey("id")
      private val NAME_KEY = stringPreferencesKey("name")
      private val USERNAME_KEY = stringPreferencesKey("username")
      private val EMAIL_KEY = stringPreferencesKey("email")
      private val TOKEN_KEY = stringPreferencesKey("token")
      private val STATE_KEY = booleanPreferencesKey("state")
   }
}