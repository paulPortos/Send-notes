package com.group1.notamonotako.views

import ApiService
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
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.NotificationAdapter
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NotificationActivity : AppCompatActivity() {
    private lateinit var btnClose : ImageButton
    private lateinit var rvNotification : RecyclerView
    private lateinit var myNotificationsAdapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        fetchNotifications()
    }

    private fun fetchNotifications(){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.showNotification()
                if (response.isSuccessful) {
                    val notifications = response.body()
                    if (notifications != null){
                        myNotificationsAdapter = NotificationAdapter (this@NotificationActivity, notifications)
                        rvNotification.adapter = myNotificationsAdapter
                    }
                    Log.d("notifications", notifications.toString())
                } else {
                    Log.d("notifications", "Failed to fetch notifications")
                }
             } catch (e: Exception) {
                 Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()

            } catch (e: HttpException){
                Toast.makeText(this@NotificationActivity, "Failed to fetch notifications", Toast.LENGTH_SHORT).show()
                Log.d("addnotes", e.message.toString())
            }
        }
    }
}