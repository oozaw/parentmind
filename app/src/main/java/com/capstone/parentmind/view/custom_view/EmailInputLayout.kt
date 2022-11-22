package com.capstone.parentmind.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.widget.EditText
import androidx.core.content.ContextCompat
import com.capstone.parentmind.R
import com.capstone.parentmind.utils.checkEmailPattern
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class EmailInputLayout: TextInputLayout {
   private lateinit var backgroundEditText: Drawable
   private lateinit var myEditText: TextInputEditText
   private var childEditText: EditText? = null

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
   }

   private fun init() {
      backgroundEditText = ContextCompat.getDrawable(context, R.drawable.bg_input_text) as Drawable

      setWillNotDraw(false);
      myEditText = TextInputEditText(context);
      createEditText(myEditText);
   }

   private fun createEditText(editText: TextInputEditText) {
      val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
      editText.setPadding(10, 10, 0, 0)
      editText.layoutParams = layoutParams

      editText.maxLines = 1
      editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
      editText.height = 50
      editText.background = backgroundEditText

      editText.addTextChangedListener(object: TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, after: Int) {
            if (checkEmailPattern(s.toString())) {
               clearError()
            } else {
               showError()
            }
         }

         override fun afterTextChanged(s: Editable?) {

         }

      })
      addView(editText)
   }

   private fun clearError() {
      isErrorEnabled = false
      error = null
   }

   private fun showError() {
      isErrorEnabled = true
      error = "Masukkan email yang valid"
   }
}