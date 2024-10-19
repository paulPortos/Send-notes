package com.group1.notamonotako.views

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class AboutActivity : AppCompatActivity() {
    private lateinit var btnback : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        btnback = findViewById(R.id.btnback)

        btnback.setOnClickListener {
            finish()
        }
    }
}