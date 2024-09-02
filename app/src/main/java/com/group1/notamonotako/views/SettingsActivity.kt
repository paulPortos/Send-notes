package com.group1.notamonotako.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import com.group1.notamonotako.api.ApiClient
import com.group1.notamonotako.api.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
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

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    clearToken()
                    Toast.makeText(
                        this@SettingsActivity,
                        "Logged Out Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@SettingsActivity, SignInActivity::class.java)
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

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    this@SettingsActivity,
                    "Network error occurred: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun clearToken() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("auth_token").apply()
    }
    private fun getToken(): String? {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("auth_token", null)
    }
}