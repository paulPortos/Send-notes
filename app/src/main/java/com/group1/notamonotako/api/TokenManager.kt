package com.group1.notamonotako.api

import android.content.Context
import android.content.SharedPreferences

class TokenManager(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_prefs" // SharedPreferences file name
        private const val TOKEN_KEY = "auth_token" // Key to store the token
    }

    // Save token to SharedPreferences
    fun saveToken(token: String) {
        val editor = preferences.edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    // Retrieve token from SharedPreferences
    fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null)
    }

    // Clear the token from SharedPreferences
    fun clearToken() {
        val editor = preferences.edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }
}
