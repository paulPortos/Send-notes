package com.group1.notamonotako.views

import ApiService
import TokenManager.clearToken
import TokenManager.getToken
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import com.group1.notamonotako.api.ApiClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContentView(R.layout.activity_settings)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        val signout = findViewById<TextView>(R.id.tvsignout)

        signout.setOnClickListener{
            logoutUser()
        }
    }

    private fun logoutUser() {
        val token = getToken() ?: run {
            Toast.makeText(this@SettingsActivity, "No token found", Toast.LENGTH_SHORT).show()
            return
        }
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
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
                    Toast.makeText(
                        this@SettingsActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(
                    this@SettingsActivity,
                    "Network error occurred: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



}