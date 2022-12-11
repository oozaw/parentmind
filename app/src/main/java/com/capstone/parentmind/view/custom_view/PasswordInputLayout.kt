package com.capstone.parentmind.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.capstone.parentmind.R
import com.capstone.parentmind.utils.checkEmailPattern
import com.google.android.material.textfield.TextInputLayout

class PasswordInputLayout: TextInputLayout {
   private lateinit var backgroundEditText: Drawable

   constructor(context: Context) : super(context) {
      init()
   }

   constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
      init()
   }

   constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
      init()
   }

   override fun onDraw(canvas: Canvas?) {
      super.onDraw(canvas)
      @Suppress("DEPRECATION")
      isPasswordVisibilityToggleEnabled = true
      editText?.maxLines = 1
      editText?.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
      editText?.height = 50
      editText?.background = backgroundEditText
   }

   private fun init() {
      backgroundEditText = ContextCompat.getDrawable(context, R.drawable.bg_input_text) as Drawable
      editText?.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            TODO("checking password")
         }

         override fun afterTextChanged(s: Editable?) {

         }

      })
   }

   private fun clearError() {
      isErrorEnabled = false
      error = null
   }

   private fun showError() {
      isErrorEnabled = true
      error = "Masukkan email yang valid"
      TODO("set error password")
   }
}