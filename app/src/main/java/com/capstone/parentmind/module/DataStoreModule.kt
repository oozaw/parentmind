package com.capstone.parentmind.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
   companion object{
      private const val PREFERENCE_NAME = "settings"

      @Singleton
      @Provides
      fun providePreferenceDataStore(
         @ApplicationContext context: Context
      ): DataStore<Preferences> {
         return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
               produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context, PREFERENCE_NAME)),
            produceFile = { context.preferencesDataStoreFile(PREFERENCE_NAME) }
         )
      }
   }
}
