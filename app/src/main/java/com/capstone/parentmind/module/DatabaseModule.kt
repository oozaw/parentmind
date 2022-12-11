package com.capstone.parentmind.module

import android.content.Context
import androidx.room.Room
import com.capstone.parentmind.data.local.database.ArticleDao
import com.capstone.parentmind.data.local.database.ArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
   @Provides
   @Singleton
   fun provideArticleDao (database: ArticleDatabase): ArticleDao {
      return database.articleDao()
   }

   @Provides
   @Singleton
   fun provideDatabase(@ApplicationContext context: Context): ArticleDatabase {
      return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java, "article_database"
         )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
   }
}
