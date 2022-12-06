package com.capstone.parentmind.data.local.preference

import android.content.Context

internal class Preferences(context: Context?) {
    companion object{
        private const val TOKEN = "token"
    }

    private val preferences = context?.getSharedPreferences(TOKEN, Context.MODE_PRIVATE)

    fun setToken(token : String) {
        val editor = preferences?.edit()
        editor?.putString(TOKEN, token)
        editor?.apply()
    }

    fun getToken(): String? {
        return preferences?.getString(TOKEN, "")
    }

    fun deleteToken () {
        val editor = preferences?.edit()
        editor?.clear()
        editor?.apply()
    }
}