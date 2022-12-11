package com.capstone.parentmind.view.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityOptionsCompat
import com.capstone.parentmind.R
import com.capstone.parentmind.databinding.ActivitySearchBinding
import com.capstone.parentmind.utils.makeToast
import com.capstone.parentmind.view.article.main.MainArticleActivity
import com.capstone.parentmind.view.video.main.MainVideoActivity
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
      checkQueryInput()
   }

   private fun setupAction() {
      binding.btnBackToolbar.setOnClickListener {
         @Suppress("DEPRECATION")
         onBackPressed()
      }

      binding.btnSearch.setOnClickListener {
         hideKeyboard()
         searchAction()
      }
   }

   private fun searchAction() {
      val query = binding.searchEditText.text.toString()
      val gender = when {
         binding.btnBoy.isFocusable -> binding.btnBoy.tag.toString()
         binding.btnGirl.isFocusable -> binding.btnGirl.tag.toString()
         else -> ""
      }
      val category = when {
         binding.btn12th.isFocusable -> binding.btn12th.tag.toString()
         binding.btn34th.isFocusable -> binding.btn34th.tag.toString()
         binding.btn56th.isFocusable -> binding.btn56th.tag.toString()
         binding.btn712th.isFocusable -> binding.btn712th.tag.toString()
         binding.btn1316th.isFocusable -> binding.btn1316th.tag.toString()
         binding.btn1721th.isFocusable -> binding.btn1721th.tag.toString()
         else -> ""
      }
      val type = when {
         binding.btnArticle.isFocusable -> binding.btnArticle.tag.toString()
         binding.btnVideo.isFocusable -> binding.btnVideo.tag.toString()
         else -> ""
      }

     if (type == "article") {
        Intent(this, MainArticleActivity::class.java).also { intent ->
           intent.putExtra(EXTRA_SEARCH, true)
           intent.putExtra(EXTRA_QUERY, query)
           intent.putExtra(EXTRA_GENDER, gender)
           intent.putExtra(EXTRA_CATEGORY, category)
           intent.putExtra(EXTRA_TYPE, type)
           startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
     } else {
        Intent(this, MainVideoActivity::class.java).also { intent ->
           intent.putExtra(EXTRA_SEARCH, true)
           intent.putExtra(EXTRA_QUERY, query)
           intent.putExtra(EXTRA_GENDER, gender)
           intent.putExtra(EXTRA_CATEGORY, category)
           intent.putExtra(EXTRA_TYPE, type)
           startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
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
      val btn12th = binding.btn12th
      val btn34th = binding.btn34th
      val btn56th = binding.btn56th
      val btn712th = binding.btn712th
      val btn1316th = binding.btn1316th
      val btn1721th = binding.btn1721th

      btn12th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn34th.isFocusable) btn34th.isFocusable = false
         if (btn56th.isFocusable) btn56th.isFocusable = false
         if (btn712th.isFocusable) btn712th.isFocusable = false
         if (btn1316th.isFocusable) btn1316th.isFocusable = false
         if (btn1721th.isFocusable) btn1721th.isFocusable = false
         setButtonStatus()
      }

      btn34th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn12th.isFocusable) btn12th.isFocusable = false
         if (btn56th.isFocusable) btn56th.isFocusable = false
         if (btn712th.isFocusable) btn712th.isFocusable = false
         if (btn1316th.isFocusable) btn1316th.isFocusable = false
         if (btn1721th.isFocusable) btn1721th.isFocusable = false
         setButtonStatus()
      }

      btn56th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn12th.isFocusable) btn12th.isFocusable = false
         if (btn34th.isFocusable) btn34th.isFocusable = false
         if (btn712th.isFocusable) btn712th.isFocusable = false
         if (btn1316th.isFocusable) btn1316th.isFocusable = false
         if (btn1721th.isFocusable) btn1721th.isFocusable = false
         setButtonStatus()
      }

      btn712th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn12th.isFocusable) btn12th.isFocusable = false
         if (btn34th.isFocusable) btn34th.isFocusable = false
         if (btn56th.isFocusable) btn56th.isFocusable = false
         if (btn1316th.isFocusable) btn1316th.isFocusable = false
         if (btn1721th.isFocusable) btn1721th.isFocusable = false
         setButtonStatus()
      }

      btn1316th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn12th.isFocusable) btn12th.isFocusable = false
         if (btn34th.isFocusable) btn34th.isFocusable = false
         if (btn56th.isFocusable) btn56th.isFocusable = false
         if (btn712th.isFocusable) btn712th.isFocusable = false
         if (btn1721th.isFocusable) btn1721th.isFocusable = false
         setButtonStatus()
      }

      btn1721th.setOnClickListener {
         it.isFocusable = !it.isFocusable
         if (btn12th.isFocusable) btn12th.isFocusable = false
         if (btn34th.isFocusable) btn34th.isFocusable = false
         if (btn56th.isFocusable) btn56th.isFocusable = false
         if (btn712th.isFocusable) btn712th.isFocusable = false
         if (btn1316th.isFocusable) btn1316th.isFocusable = false
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

   private fun checkQueryInput() {
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

   companion object {
      const val EXTRA_SEARCH = "search"
      const val EXTRA_QUERY = "query"
      const val EXTRA_GENDER = "gender"
      const val EXTRA_CATEGORY = "category"
      const val EXTRA_TYPE = "type"
   }
}