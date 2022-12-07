package com.capstone.parentmind.view.video.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.capstone.parentmind.data.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVideoViewModel @Inject constructor(private val repository: VideoRepository): ViewModel() {
   fun videoPaging() = repository.getVideoPaging().cachedIn(viewModelScope)
}