package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R

class Mynotes : AppCompatActivity() {

    private lateinit var Title :EditText
    private lateinit var Content :EditText
    private lateinit var Date : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_mynotes)
        Date = findViewById(R.id.date)
        Title = findViewById(R.id.Title)
        Content = findViewById(R.id.Contents)



        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")

        this.Title.setText(title)
        this.Content.setText(contents)


    }
}