package com.group1.notamonotako.views

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.group1.notamonotako.R

class ViewFlashcards : AppCompatActivity() {
    private var contentsList: MutableList<String> = mutableListOf()
    private var currentIndex: Int = 0
    private lateinit var timestamp: TextView
    private lateinit var title: EditText
    private lateinit var contents: EditText
    private lateinit var btnCheck: Button //
    private lateinit var btnRight: ImageButton
    private lateinit var btnLeft: ImageButton
    private lateinit var btnBack: ImageButton //
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_flashcards)

        title = findViewById(R.id.title)
        contents = findViewById(R.id.contents)
        btnCheck = findViewById(R.id.btn_check) //
        btnRight = findViewById(R.id.btn_right)
        btnLeft = findViewById(R.id.btn_left)
        btnBack = findViewById(R.id.btn_back) //
        timestamp = findViewById(R.id.timestamp)
        viewPager = findViewById(R.id.viewPager)
        viewPager.setUserInputEnabled(true)

        // Safely retrieve extras
        val flashcardTitle = intent.getStringExtra("title")
        contentsList = intent.getStringArrayListExtra("cards")?.toMutableList() ?: mutableListOf()



        //Log the contentsList
        Log.d("ViewFlashcards", "Contents List: $contentsList")

        if (flashcardTitle != null) {
            title.setText(flashcardTitle) // Set the title if it's available
            contents.setText(contentsList[currentIndex]) // Set the first content
        } else {
            Log.e("ViewFlashcards", "No title received in intent")
        }

        btnLeft.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                contents.setText(contentsList[currentIndex]) // Show previous item
                btnLeft.isEnabled = currentIndex > 0 // Disable if at the first item
            } else {
                Toast.makeText(this, "No previous item.", Toast.LENGTH_SHORT).show()
                btnLeft.isEnabled = false // Disable left button if we're at the first item
            }
        }

        btnRight.setOnClickListener {
            val content = contents.text.toString()
            if (content.isNotBlank()) {
                if (currentIndex < contentsList.size - 1) {
                    contentsList[currentIndex] = content // Update the current item
                    currentIndex++ // Move to the next index
                    contents.setText(contentsList[currentIndex]) // Load next item
                } else {
                    // Add new content
                    addToContentsList(content)
                    contents.text.clear() // Clear input
                    currentIndex++ // Move to the next index
                }
                btnLeft.isEnabled = currentIndex > 0 // Enable left button if applicable
            } else {
                Toast.makeText(this, "Card is blank.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToContentsList(contents: String) {
        contentsList.add(contents)
    }
}
