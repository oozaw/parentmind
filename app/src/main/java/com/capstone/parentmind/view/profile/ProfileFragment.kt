package com.capstone.parentmind.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.capstone.parentmind.R
import com.capstone.parentmind.data.remote.response.User
import com.capstone.parentmind.databinding.FragmentProfileBinding
import com.capstone.parentmind.view.home.HomeActivity
import com.capstone.parentmind.view.home.HomeViewModel
import com.capstone.parentmind.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
   private var _binding: FragmentProfileBinding? = null
   private val binding get() = _binding!!

   private lateinit var auth: FirebaseAuth

   private val viewModel: HomeViewModel by viewModels()

   private lateinit var user: User

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

      auth = Firebase.auth

      setupAction()
      setupView()
   }

   override fun onStart() {
      super.onStart()


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

   private fun setupView() {
      viewModel.getUser().observe(viewLifecycleOwner) {  user ->
         binding.tvFullnameProfile.text = user.name
         binding.tvEmailProfile.text = user.email
      }
   }

   private fun logout() {
      auth.signOut()
      viewModel.logout()
      Intent(requireActivity(), LoginActivity::class.java).also { intent ->
         intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
         startActivity(intent)
         requireActivity().finish()
      }
   }
}