package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
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
    private lateinit var btnBack: ImageButton
    private lateinit var title : EditText
    private lateinit var contents : EditText
    private lateinit var btnCheck : Button
    private lateinit var gestureDetector: GestureDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards) // Use your activity layout

        // Lock orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewPager = findViewById(R.id.viewPager)
        btnBack = findViewById(R.id.btn_back)
        title = findViewById(R.id.title)
        contents = findViewById(R.id.contents)
        btnCheck = findViewById(R.id.btn_check)

        viewPager.setUserInputEnabled(false)

        // Initialize the gesture detector using an anonymous class
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,  // Make both e1 and e2 nullable
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null && e2 != null) {  // Safely check if both are non-null
                    val diffX = e2.x - e1.x
                    val diffY = e2.y - e1.y
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        // Detect left or right swipe
                        if (diffX > 0) {
                            if (currentIndex > 0) {  // Ensure currentIndex doesn't go below 0
                                currentIndex--
                                // Move to the previous index
                                contents.setText(contentsList[currentIndex])  // Show the previous item for editing
                            } else {
                                Toast.makeText(this@AddFlashcards, "No previous item.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
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
                            } else {
                                Toast.makeText(this@AddFlashcards, "Card is blank.", Toast.LENGTH_SHORT).show()
                            }
                        }
                        return true
                    }
                }
                return false
            }
        })

        btnCheck.setOnClickListener {
            val content = contents.text.toString()
            if (content.isNotBlank()) {
                addToContentsList(content)
            }
            //log content
            Log.d("AddFlashcards", "Content List: $contentsList")
            if(title.text.toString().isNotBlank() && contentsList.isNotEmpty()){
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

        btnBack.setOnClickListener {
           val intent = Intent(this@AddFlashcards, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            gestureDetector.onTouchEvent(it)
        }
        return super.onTouchEvent(event)
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
                Log.e("AddFlashcards", "Error: ${e.message}", e)
            } catch (e: HttpException){
                Log.e("AddFlashcards", "HTTP Error: ${e.message()}", e)
            }
        }
    }

    private fun addToContentsList(contents: String) {
        contentsList.add(contents)
    }
}
