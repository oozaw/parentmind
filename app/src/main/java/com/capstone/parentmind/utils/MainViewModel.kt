package com.capstone.parentmind.utils

import androidx.lifecycle.ViewModel
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.view.article.ArticleRepository

class MainViewModel(private val articleRepository: ArticleRepository) : ViewModel() {
    fun getAllArticles() = articleRepository.getAllArticles()

    fun getDetailArticles(id: Int) = articleRepository.getDetailArticles(id)

    fun saveArticle(article: ArticleEntity) {
        articleRepository.setBookmarkedArticle(article, true)
    }

    fun deleteArticle(article: ArticleEntity) {
        articleRepository.setBookmarkedArticle(article, false)
    }

    fun checkSave(id: Int) = articleRepository.checkSave(id)
}