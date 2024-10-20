package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class ViewSendNotes : AppCompatActivity() {
    private lateinit var btncopy : AppCompatButton
    private lateinit var btndelete : ImageButton
    private lateinit var btnback : ImageButton
    private lateinit var sent_by : TextView
    private lateinit var title: TextView
    private lateinit var time:  TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_send_notes)

        btnback = findViewById(R.id.btnback)
        btndelete = findViewById(R.id.btndelete)
        btncopy = findViewById(R.id.btncopy)
        sent_by = findViewById(R.id.sent_by)
        title = findViewById(R.id.title)
        time = findViewById(R.id.time)

        btnback.setOnClickListener {
            finish()
        }

    }
}