package com.group1.notamonotako.api

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object AccountManager {
    private const val PREFS_NAME = "user_prefs"
    private const val EMAIL_KEY = "email"
    private const val USERNAME_KEY = "username"
    private lateinit var preferences: SharedPreferences
    // Initialize the AccountManager with context
    fun init(context: Context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    // Save the user's email
    fun saveEmail(email: String) {
        Log.d("AccountManager", "Saving email: $email")
        preferences.edit().putString(EMAIL_KEY, email).apply()
    }
    // Save the user's username
    fun saveUsername(username: String) {
        Log.d("AccountManager", "Saving username: $username")
        preferences.edit().putString(USERNAME_KEY, username).apply()
    }
    // Retrieve the stored email
    fun getEmail(): String? {
        val email = preferences.getString(EMAIL_KEY, null)
        Log.d("AccountManager", "Retrieved email: $email")
        return email
    }
    // Retrieve the stored username
    fun getUsername(): String? {
        val username = preferences.getString(USERNAME_KEY, null)
        Log.d("AccountManager", "Retrieved username: $username")
        return username
    }
}