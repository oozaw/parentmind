package com.capstone.parentmind.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.FragmentProfileBinding
import com.capstone.parentmind.view.home.HomeActivity
import com.capstone.parentmind.view.home.HomeViewModel
import com.capstone.parentmind.view.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
   private var _binding: FragmentProfileBinding? = null
   private val binding get() = _binding!!

   private val viewModel: HomeViewModel by viewModels()

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupAction() {
      binding.tvLogoutProfile.setOnClickListener {
         logout()
      }

      binding.ivLogoutProfile.setOnClickListener {
         logout()
      }
   }

   private fun logout() {
      HomeActivity.auth.signOut()
      viewModel.logout()
      Intent(requireActivity(), LoginActivity::class.java).also { intent ->
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(intent)
         requireActivity().finish()
      }
   }
}