package com.group1.notamonotako.views

import ApiService
import TokenManager.clearToken
import TokenManager.getToken
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.group1.notamonotako.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var progressBar : ProgressBar
    private lateinit var btnsign_out : AppCompatButton
    private lateinit var sounds : Switch

    private val PREFS_NAME = "com.group1.notamonotako.PREFERENCES"
    private val SOUND_MUTED_KEY = "sound_muted"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContentView(R.layout.activity_settings)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnsign_out = findViewById(R.id.btnsign_out)
        sounds = findViewById(R.id.sounds)
        progressBar = findViewById(R.id.progressBar)
        mediaPlayer = MediaPlayer.create(this,R.raw.soundeffects)

        progressBar.visibility = View.INVISIBLE

        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val soundIsMuted = sharedPreferences.getBoolean(SOUND_MUTED_KEY, false)

        sounds.isChecked = !soundIsMuted
        updateMediaPlayerVolume(soundIsMuted)

        sounds.setOnCheckedChangeListener { _, isChecked ->
            saveSoundPreference(!isChecked)
            updateMediaPlayerVolume(!isChecked)
        }

        btnsign_out.setOnClickListener {
            logoutUser()
            progressBar.visibility = View.VISIBLE
            mediaPlayer.start()
        }
    }

    private fun saveSoundPreference(isMuted: Boolean) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SOUND_MUTED_KEY, isMuted)
        editor.apply()
    }

    private fun updateMediaPlayerVolume(isMuted: Boolean) {
        if (isMuted) {
            mediaPlayer.setVolume(0F, 0F)
        } else
        {
            mediaPlayer.setVolume(1F, 1F)
        }
    }


    private fun logoutUser() {
        val token = getToken() ?: run {
            Toast.makeText(this@SettingsActivity, "No token found", Toast.LENGTH_SHORT).show()
            return
        }
        val apiService = RetrofitInstance.create(ApiService::class.java)
        val call = apiService.logout("Bearer $token")

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    clearToken()
                    Toast.makeText(
                        this@SettingsActivity,
                        "Logged Out Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@SettingsActivity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(
                        this@SettingsActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(
                    this@SettingsActivity,
                    "Network error occurred: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



}