package com.capstone.parentmind.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
   private var _binding: ActivityHomeBinding? = null
   private val binding get()= _binding!!

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      _binding = ActivityHomeBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setSupportActionBar(binding.toolbar)

      val bottomNav = binding.bottomNav
      val navController: NavController = findNavController(R.id.nav_host_fragment)

      val appBarConfiguration = AppBarConfiguration.Builder(
         R.id.nav_home, R.id.nav_article, R.id.nav_forum, R.id.nav_consult, R.id.nav_profile
      ).build()

      setupActionBarWithNavController(navController, appBarConfiguration)
      bottomNav.setupWithNavController(navController)
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}