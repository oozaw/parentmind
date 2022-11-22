package com.capstone.parentmind.utils

import android.util.Patterns

fun checkEmailPattern(input: String): Boolean {
   return input.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(input).matches()
}