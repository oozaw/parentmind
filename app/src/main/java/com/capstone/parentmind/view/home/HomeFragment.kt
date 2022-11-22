package com.capstone.parentmind.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
   private var _binding: FragmentHomeBinding? = null
   private val binding get()= _binding!!

   private lateinit var auth: FirebaseAuth

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      auth = Firebase.auth
   }

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_home, container, false)
   }

//   override fun onResume() {
//      super.onResume()
//      (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
//   }
//
//   override fun onStop() {
//      super.onStop()
//      (activity as AppCompatActivity?)!!.supportActionBar!!.show()
//   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }
}