package com.capstone.parentmind.view.video.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.capstone.parentmind.data.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVideoViewModel @Inject constructor(private val repository: ArticleRepository): ViewModel() {
   fun videoPaging() = repository.getArticlePaging("video").cachedIn(viewModelScope)
}