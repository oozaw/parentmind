package com.capstone.parentmind.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivityHomeBinding
import com.capstone.parentmind.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
   private var _binding: ActivityHomeBinding? = null
   private val binding get()= _binding!!

   private lateinit var auth: FirebaseAuth

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = ActivityHomeBinding.inflate(layoutInflater)
      setContentView(binding.root)

      auth = Firebase.auth

      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupAction() {
      binding.btnToLoginPage.setOnClickListener {
         val loginIntent = Intent(this, LoginActivity::class.java)
         startActivity(loginIntent)
      }

      binding.btnLogout.setOnClickListener {
         auth.signOut()
      }
   }
}