package com.group1.notamonotako.views

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import java.text.SimpleDateFormat
import java.util.Locale

class Mynotes : AppCompatActivity() {

    private lateinit var Title: EditText
    private lateinit var Content: EditText
    private lateinit var Date: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mynotes)

        Date = findViewById(R.id.date)
        Title = findViewById(R.id.Title)
        Content = findViewById(R.id.Contents)

        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val dateString = intent.getStringExtra("date")

        this.Title.setText(title)
        this.Content.setText(contents)

        if (dateString != null) {
            // Define the input and output date formats
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            try {
                val date = inputFormat.parse(dateString)
                val formattedDate = date?.let { outputFormat.format(it) }
                this.Date.text = formattedDate
            } catch (e: Exception) {
                // Handle parsing exception
                e.printStackTrace()
                this.Date.text = "Invalid date"
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("showMyNotesFragment", true)  // Pass data to indicate the Home fragment should be shown
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()  // Finish Mynotes activity to avoid going back to it
    }
}