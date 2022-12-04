package com.capstone.parentmind.view.video

import androidx.lifecycle.ViewModel
import com.capstone.parentmind.data.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainVideoViewModel @Inject constructor(private val repository: VideoRepository): ViewModel() {
   fun getVideos() = repository.getVideos()
}