package com.capstone.parentmind.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

fun checkEmailPattern(input: String): Boolean {
   return input.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
}

fun makeToast(context: Context, text: String) {
   Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun extractVideoId(ytUrl: String): String? {
   var videoId: String? = null
   val regex =
      "^((?:https?:)?//)?((?:www|m)\\.)?((?:youtube\\.com|youtu.be|youtube-nocookie.com))(/(?:[\\w\\-]+\\?v=|feature=|watch\\?|e/|embed/|v/)?)([\\w\\-]+)(\\S+)?\$"
   val pattern: Pattern = Pattern.compile(
      regex ,
      Pattern.CASE_INSENSITIVE
   )
   val matcher: Matcher = pattern.matcher(ytUrl)
   if (matcher.matches()) {
      videoId = matcher.group(5)
   }

   return videoId
}