package com.capstone.parentmind.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.parentmind.R

class RoundedButton: AppCompatButton {
   private var color: Int? = null
   private lateinit var enableBackground: Drawable
   private lateinit var disableBackground: Drawable

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
      color?.let { setTextColor(it) }
      background = if(isEnabled) enableBackground else disableBackground
      setPadding(0, 1, 0, 0)
      textSize = 16f
      height = 35
      isAllCaps = false
      gravity = Gravity.CENTER
   }

   private fun init() {
      color = ContextCompat.getColor(context, R.color.white)
      enableBackground = ContextCompat.getDrawable(context, R.drawable.enable_button) as Drawable
      disableBackground = ContextCompat.getDrawable(context, R.drawable.disable_button) as Drawable
   }
}