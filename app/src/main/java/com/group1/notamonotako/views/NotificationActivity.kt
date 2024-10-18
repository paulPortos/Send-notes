package com.group1.notamonotako.views

import ApiService
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.NotificationAdapter
import com.group1.notamonotako.api.AccountManager
import com.group1.notamonotako.api.SoundManager
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NotificationActivity : AppCompatActivity() {
    private lateinit var btnClose : ImageButton
    private lateinit var rvNotification : RecyclerView
    private lateinit var myNotificationsAdapter: NotificationAdapter
    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        rvNotification = findViewById(R.id.rvNotification)

        soundManager = SoundManager(this) // Initialize SoundManager
        val isMuted = AccountManager.isMuted
        soundManager.updateMediaPlayerVolume(isMuted)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        rvNotification.layoutManager = LinearLayoutManager(this@NotificationActivity)
        fetchNotifications()

        btnClose = findViewById(R.id.btnClose)

        btnClose.setOnClickListener{
            finish()
            soundManager.playSoundEffect()

        }
    }

    private fun fetchNotifications(){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.showNotification()
                if (response.isSuccessful) {
                    val notifications = response.body() ?: emptyList()
                    myNotificationsAdapter = NotificationAdapter (this@NotificationActivity, notifications)
                    rvNotification.adapter = myNotificationsAdapter
                    Log.d("getNotifications", notifications.toString())
                } else {
                    Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                    Log.d("notifications", "Response code: ${response.code()}") // Log the response code
                    Log.d("notifications", "Error body: ${response.errorBody()?.string()}") // Log error body
                    Log.d("notifications", response.message())
                    Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                }
             } catch (e: Exception) {
                 Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                Log.d("notifications", e.message.toString())
            } catch (e: HttpException){
                Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                Log.d("notifications", e.message.toString())
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        soundManager.release() // Release media player when done
    }
}