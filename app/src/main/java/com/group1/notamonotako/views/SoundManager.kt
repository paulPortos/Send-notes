package com.group1.notamonotako.views

import android.content.Context
import android.content.SharedPreferences

object SoundManager {
    private const val PREFS_NAME = "com.group1.notamonotako.PREFERENCES"
    private const val SOUND_MUTED_KEY = "sound_muted"

    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isMuted: Boolean
        get() = sharedPreferences.getBoolean(SOUND_MUTED_KEY, false)
        set(value) {
            sharedPreferences.edit().putBoolean(SOUND_MUTED_KEY, value).apply()
        }

    fun toggleSound() {
        isMuted = !isMuted
    }
}
