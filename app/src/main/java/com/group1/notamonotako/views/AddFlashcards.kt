package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.flashcards.PostFlashcards
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AddFlashcards : AppCompatActivity() {
    private val contentsList: MutableList<String> = mutableListOf()
    private var currentIndex: Int = 0  // Track the current index, starting at -1
    private lateinit var viewPager: ViewPager2
    private lateinit var btnRight: ImageButton
    private lateinit var btnLeft : ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var title : EditText
    private lateinit var contents : EditText
    private lateinit var btnCheck : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards) // Use your activity layout

        // Lock orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btn_back)
        btnRight = findViewById(R.id.btn_right)
        btnLeft = findViewById(R.id.btn_left)
        title = findViewById(R.id.title)
        contents = findViewById(R.id.contents)
        btnCheck = findViewById(R.id.btn_check)

        viewPager.setUserInputEnabled(false)
        btnLeft.isEnabled = currentIndex > 0

        btnCheck.setOnClickListener {
            val content = contents.text.toString()
            addToContentsList(content)
            //log content
            Log.d("AddFlashcards", "Content List: $contentsList")
            if(title.text.toString().isNotBlank() && contentsList.isNotEmpty()){
                if (content.isNotBlank()) {

                }
                val title = title.text.toString()
                val public = false
                val toPublic = false
                createData(title, contentsList, public, toPublic)
                val intent = Intent(this@AddFlashcards, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Title and cards are required", Toast.LENGTH_SHORT).show()
            }
        }
        //1 2 3
        btnLeft.setOnClickListener {
            if (currentIndex > 0) {  // Ensure currentIndex doesn't go below 0
                currentIndex--
                  // Move to the previous index
                contents.setText(contentsList[currentIndex])  // Show the previous item for editing
                btnLeft.isEnabled = currentIndex > 0  // Disable left button if no more previous items
            } else {
                Toast.makeText(this, "No previous item.", Toast.LENGTH_SHORT).show()
                btnLeft.isEnabled = false  // Disable the button if we're at the first item
            }
        }

        //0 1 2 3
        btnRight.setOnClickListener {
            val content = contents.text.toString()

            if (content.isNotBlank()) {
                if (currentIndex == contentsList.size) {
                    // If currentIndex is at the end, add a new content item
                    addToContentsList(content)
                    contents.text.clear()  // Clear the input field after adding
                    currentIndex++  // Move to the next index
                } else if (currentIndex < contentsList.size) {
                    // If currentIndex is still within the list, move to the next item
                    contentsList[currentIndex] = content  // Update the current item with new content
                    currentIndex++  // Move to the next index
                    if (currentIndex < contentsList.size) {
                        contents.setText(contentsList[currentIndex])  // Load the next item for editing
                    } else {
                        contents.text.clear()  // Clear the input if no more items
                    }
                }
                btnLeft.isEnabled = currentIndex > 0  // Enable the left button if applicable
            } else {
                Toast.makeText(this, "Card is blank.", Toast.LENGTH_SHORT).show()
            }
        }

        btnBack.setOnClickListener {
           val intent = Intent(this@AddFlashcards, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createData(title: String, cards: MutableList<String>, public: Boolean, toPublic: Boolean) {
        lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val flashcardsData =
                PostFlashcards(title = title, cards = cards, public = public, to_public = toPublic)
            try {
                val response = apiService.postFlashcards(flashcardsData)
                if (response.isSuccessful) {
                    Toast.makeText(this@AddFlashcards, "Flashcards created successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddFlashcards, "Failed to create flashcards", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } catch (e: HttpException){
                e.printStackTrace()
            }
        }
    }
    private fun addToContentsList(contents: String) {
        contentsList.add(contents)
    }
}
