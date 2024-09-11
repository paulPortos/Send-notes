package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class Mynotes : AppCompatActivity() {

    private lateinit var Title :EditText
    private lateinit var Content :EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mynotes)

        Title = findViewById(R.id.Title)
        Content = findViewById(R.id.Contents)



        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")

        this.Title.setText(title)
        this.Content.setText(contents)


    }
}