package com.capstone.parentmind.view.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.capstone.parentmind.R

class SearchOptionButton: AppCompatButton {
   private var color: Int? = null
   private var enableTextColor: Int? = null
   private var disableTextColor: Int? = null
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
      color = if(isFocusable) enableTextColor else disableTextColor
      color?.let { setTextColor(it) }
      background = if(isFocusable) enableBackground else disableBackground
      setPadding(0, 0, 0, 1)
      textSize = 12f
      isAllCaps = false
      gravity = Gravity.CENTER
   }

   private fun init() {
      enableTextColor = ContextCompat.getColor(context, R.color.white)
      disableTextColor = ContextCompat.getColor(context, R.color.blue)
      enableBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_solid) as Drawable
      disableBackground = ContextCompat.getDrawable(context, R.drawable.bg_button_stroke) as Drawable
   }
}