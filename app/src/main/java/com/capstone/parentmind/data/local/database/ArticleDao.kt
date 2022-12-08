package com.capstone.parentmind.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article ORDER BY id ASC")
    fun getAllArticles(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticle(article: List<ArticleEntity>)

    @Update
    fun updateArticle(article: ArticleEntity)

//    @Query("DELETE FROM article WHERE bookmark = 0")
//    fun deleteAll()
//
//    @Query("SELECT EXISTS(SELECT * FROM article WHERE id = :id AND bookmark = 1)")
//    fun isArticleBookmarked(id: Int): Boolean
}