package com.capstone.parentmind.view.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.parentmind.databinding.FragmentHomeBinding
import com.capstone.parentmind.view.video.main.MainVideoActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
   private var _binding: FragmentHomeBinding? = null
   private val binding get()= _binding!!

   private lateinit var auth: FirebaseAuth

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentHomeBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)

      auth = Firebase.auth

      binding.cvArticleFeature.setOnClickListener {
//         Intent(activity, HomeFragment::class.java).also {
//            startActivity(it)
//         }
      }
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}