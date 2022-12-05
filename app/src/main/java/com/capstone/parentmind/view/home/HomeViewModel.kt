package com.capstone.parentmind.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.parentmind.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val userRepository: UserRepository
): ViewModel() {

   fun checkLogin(): LiveData<Boolean> = userRepository.checkStateLogin().asLiveData()

   fun logout() {
      viewModelScope.launch {
         userRepository.logout()
      }
   }
}