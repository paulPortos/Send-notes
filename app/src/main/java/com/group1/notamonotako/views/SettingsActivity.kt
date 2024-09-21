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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.content.SharedPreferences
import retrofit2.awaitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var progressBar : ProgressBar
    private lateinit var btnsign_out : AppCompatButton
    private lateinit var sounds : Switch
    private lateinit var tvemail2: TextView
    private lateinit var tvusername2: TextView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var retrofitService: ApiService

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
        tvemail2 = findViewById(R.id.tvemail2)
        tvusername2 = findViewById(R.id.tvusername2)
        retrofitService = RetrofitInstance.create(ApiService::class.java)


        // Making the progressbar Invisible
        progressBar.visibility = View.INVISIBLE

        // Load the saved sound preference state from SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val soundIsMuted = sharedPreferences.getBoolean(SOUND_MUTED_KEY, false)

        // Set the initial state of the switch and media player volume
        sounds.isChecked = !soundIsMuted
        updateMediaPlayerVolume(soundIsMuted)

        // Handle switch toggle to save sound preference and update media volume
        sounds.setOnCheckedChangeListener { _, isChecked ->
            saveSoundPreference(!isChecked)
            updateMediaPlayerVolume(!isChecked)
        }

        fetchUserData()

        btnsign_out.setOnClickListener {
            logoutUser()
            progressBar.visibility = View.VISIBLE
            mediaPlayer.start()
        }
    }

    // Save the sound preference (muted or not) in SharedPreferences
    private fun saveSoundPreference(isMuted: Boolean) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(SOUND_MUTED_KEY, isMuted)
        editor.apply()
    }

    // Update the media player's volume based on the sound preference
    private fun updateMediaPlayerVolume(isMuted: Boolean) {
        if (isMuted) {
            mediaPlayer.setVolume(0F, 0F)
        } else
        {
            mediaPlayer.setVolume(1F, 1F)
        }
    }

    // Getting the user data to show in Account Settings
    private fun fetchUserData() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val token = getToken() ?: return@launch
                val response = retrofitService.getUserData("Bearer $token")

                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let {
                        saveEmail(it.email)
                        saveUsername(it.username)
                        launch(Dispatchers.Main) {
                            showEmail()
                            showUsername()
                        }
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                    launch(Dispatchers.Main) {
                        Toast.makeText(this@SettingsActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                launch(Dispatchers.Main) {
                    Toast.makeText(this@SettingsActivity, "An error occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // Saving the email
    private fun saveEmail(email: String) {
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.apply()
    }

    // Saving the username
    private fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()
        editor.putString("username", username)
        editor.apply()
    }

    // Showing the email in Account Settings
    private fun showEmail() {
        val storedEmail = sharedPreferences.getString("email", "")
        tvemail2.text = storedEmail
    }

    // Showing the username in Account Settings
    private fun showUsername() {
        val storedUsername = sharedPreferences.getString("username", "")
        tvusername2.text = storedUsername
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