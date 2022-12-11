package com.capstone.parentmind.view.article.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.capstone.parentmind.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainArticleViewModel @Inject constructor (private val repository: ArticleRepository) : ViewModel() {

    fun getArticles(type: String) = repository.getArticle(type).cachedIn(viewModelScope)

    fun getAllArticles() = repository.getAllTypeArticle().cachedIn(viewModelScope)

    fun getSearchArticles(query: String, gender: String, type: String, category: String) = repository.getSpecificArticle(query = query, gender = gender, type = type, category = category)
}