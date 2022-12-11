package com.capstone.parentmind.view.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.capstone.parentmind.data.Result
import com.capstone.parentmind.data.UserRepository
import com.capstone.parentmind.data.remote.response.BasicResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterVIewModel @Inject constructor(
   private val userRepository: UserRepository,
): ViewModel() {

   fun register(name: String, username: String, email: String, password: String) = userRepository.register(name, username, email, password)
}