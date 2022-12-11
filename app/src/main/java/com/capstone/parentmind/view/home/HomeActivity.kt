package com.capstone.parentmind.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivityHomeBinding
import com.capstone.parentmind.utils.makeToast
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

   private val viewModel: HomeViewModel by viewModels()

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setTheme(R.style.Theme_ParentMind)
      _binding = ActivityHomeBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbar)

      auth = Firebase.auth

      val bottomNav = binding.bottomNav
      val navController: NavController = findNavController(R.id.nav_host_fragment)

      val appBarConfiguration = AppBarConfiguration.Builder(
         R.id.nav_home, R.id.nav_article, R.id.nav_forum, R.id.nav_consult, R.id.nav_profile
      ).build()

      setupActionBarWithNavController(navController, appBarConfiguration)
      bottomNav.setupWithNavController(navController)
   }

   override fun onStart() {
      super.onStart()

      viewModel.checkLogin().observe(this) { isLogin ->
         val isAuth = auth.currentUser != null
         if (!(isLogin && isAuth)) {
            Intent(this, LoginActivity::class.java).also { intent ->
               startActivity(intent)
               finish()
            }
         }
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}