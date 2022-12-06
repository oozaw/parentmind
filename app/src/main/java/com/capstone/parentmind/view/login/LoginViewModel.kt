package com.capstone.parentmind.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.parentmind.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

   fun login(email: String, password: String) = userRepository.login(email, password)

   fun loginFB(name: String, email: String, password: String) = userRepository.loginFB(name, email, password)

   fun isLogin(): LiveData<Boolean> = userRepository.checkStateLogin().asLiveData()
}