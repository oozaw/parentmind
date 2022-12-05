package com.capstone.parentmind.module

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.capstone.parentmind.data.local.preference.AuthPreference
import com.capstone.parentmind.data.local.preference.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserPreferenceModule {
   companion object {
      @Singleton
      @Provides
      fun provideUserPreference(
         dataStore: DataStore<Preferences>
      ): AuthPreference {
         return UserPreference(dataStore)
      }
   }
}