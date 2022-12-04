package com.capstone.parentmind.view.article

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.FragmentArticleBinding
import com.capstone.parentmind.view.video.main.MainVideoActivity

class ArticleFragment : Fragment() {

   private var _binding: FragmentArticleBinding? = null
   private val binding get() = _binding!!

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View {
      // Inflate the layout for this fragment
      _binding = FragmentArticleBinding.inflate(layoutInflater, container, false)
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
      binding.btnVideo.setOnClickListener {
         Intent(requireActivity(), MainVideoActivity::class.java).also { intent ->
            startActivity(intent)
         }
      }
   }
}