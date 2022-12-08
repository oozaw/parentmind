package com.capstone.parentmind.view.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivitySearchBinding
import com.capstone.parentmind.utils.checkEmailPattern
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
   private var _binding: ActivitySearchBinding? = null
   private val binding get() = _binding!!

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setTheme(R.style.Theme_ParentMind)
      _binding = ActivitySearchBinding.inflate(layoutInflater)
      setContentView(binding.root)

      setupView()
      setupAction()
   }

   override fun onDestroy() {
      super.onDestroy()
      _binding = null
   }

   private fun setupView() {
      validateGenderButton()
      validateCategoryButton()
      validateTypeButton()
      checkEmailInput()
   }

   private fun setupAction() {
      binding.btnBackToolbar.setOnClickListener {
         @Suppress("DEPRECATION")
         onBackPressed()
      }

      binding.btnSearch.setOnClickListener {
         hideKeyboard()
      }
   }

   private fun validateGenderButton() {
      val boy = binding.btnBoy
      val girl = binding.btnGirl

      boy.setOnClickListener {
         boy.isFocusable = !boy.isFocusable
         if (girl.isFocusable) girl.isFocusable = false
         setButtonStatus()
      }

      girl.setOnClickListener {
         girl.isFocusable = !girl.isFocusable
         if (boy.isFocusable) boy.isFocusable = false
         setButtonStatus()
      }
   }

   private fun validateCategoryButton() {
      binding.btn12th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }

      binding.btn34th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }

      binding.btn56th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }

      binding.btn712th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }

      binding.btn1316th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }

      binding.btn1721th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         setButtonStatus()
      }
   }

   private fun validateTypeButton() {
      val article = binding.btnArticle
      val video = binding.btnVideo

      article.setOnClickListener {
         article.isFocusable = !article.isFocusable
         if (video.isFocusable) video.isFocusable = false
         setButtonStatus()
      }

      video.setOnClickListener {
         video.isFocusable = !video.isFocusable
         if (article.isFocusable) article.isFocusable = false
         setButtonStatus()
      }
   }

   private fun setButtonStatus() {
      val isInputNull = binding.searchEditText.text.isNullOrEmpty()
      val boy = binding.btnBoy.isFocusable
      val girl = binding.btnGirl.isFocusable
      val c1 = binding.btn12th.isFocusable
      val c2 = binding.btn34th.isFocusable
      val c3 = binding.btn56th.isFocusable
      val c4 = binding.btn712th.isFocusable
      val c5 = binding.btn1316th.isFocusable
      val c6 = binding.btn1721th.isFocusable
      val article = binding.btnArticle.isFocusable
      val video = binding.btnVideo.isFocusable

      binding.btnSearch.isEnabled =
         (!isInputNull or boy or girl or c1 or c2 or c3 or c4 or c5 or c6 or article or video)
   }

   private fun checkEmailInput() {
      val inputLayout = binding.searchInputLayout
      val editText = inputLayout.editText
      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            if (s.isNullOrEmpty()) {
               setButtonStatus()
            } else {
               setButtonStatus()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }
      })
   }

   private fun hideKeyboard() {
      val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
      val windowHeightMethod = InputMethodManager::class.java.getMethod("getInputMethodWindowVisibleHeight")
      val height = windowHeightMethod.invoke(imm) as Int
      @Suppress("DEPRECATION")
      if(height > 0) imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
   }
}