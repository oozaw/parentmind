package com.capstone.parentmind.view.article.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.capstone.parentmind.data.local.database.ArticleEntity
import com.capstone.parentmind.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainArticleViewModel @Inject constructor (private val repository: ArticleRepository) : ViewModel() {

    fun getArticles(type: String) = repository.getArticlePaging(type).cachedIn(viewModelScope)

//    fun getAllArticles() = articleRepository.getAllArticles()
//
//    fun getDetailArticles(id: Int) = articleRepository.getDetailArticles(id)
//
//    fun saveArticle(article: ArticleEntity) {
//        articleRepository.setBookmarkedArticle(article, true)
//    }
//
//    fun deleteArticle(article: ArticleEntity) {
//        articleRepository.setBookmarkedArticle(article, false)
//    }
//
//    fun checkSave(id: Int) = articleRepository.checkSave(id)
}