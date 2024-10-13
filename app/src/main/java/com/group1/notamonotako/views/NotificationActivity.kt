package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R

class NotificationActivity : AppCompatActivity() {
    private lateinit var btnClose : ImageButton
    private lateinit var rvNotification : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        btnClose = findViewById(R.id.btnClose)

        btnClose.setOnClickListener{
            finish()
        }
    }
}