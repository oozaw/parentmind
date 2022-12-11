package com.capstone.parentmind.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.FragmentHomeBinding
import com.capstone.parentmind.view.article.ArticleFragment
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

      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupAction() {
      binding.cvArticleFeature.setOnClickListener {
         val view: View? = activity?.findViewById(R.id.nav_article)
         view?.performClick()
      }

      binding.cvConsultFeature.setOnClickListener {
         val view: View? = activity?.findViewById(R.id.nav_consult)
         view?.performClick()
      }

      binding.cvForumFeature.setOnClickListener {
         val view: View? = activity?.findViewById(R.id.nav_forum)
         view?.performClick()
      }
   }
}