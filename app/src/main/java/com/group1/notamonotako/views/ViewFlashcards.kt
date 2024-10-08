    package com.group1.notamonotako.views
    
    import ApiService
    import TokenManager
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.widget.Button
    import android.widget.EditText
    import android.widget.ImageButton
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.lifecycleScope
    import androidx.viewpager2.widget.ViewPager2
    import com.group1.notamonotako.R
    import com.group1.notamonotako.api.requests_responses.flashcards.UpdateFlashcards
    import com.group1.notamonotako.fragments.MyFlashcards
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlinx.coroutines.withContext
    import retrofit2.HttpException
    
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
        private lateinit var btnDelete: Button
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_view_flashcards)
    
            title = findViewById(R.id.title)
            contents = findViewById(R.id.contents)
            btnCheck = findViewById(R.id.btn_check)
            btnRight = findViewById(R.id.btn_right)
            btnLeft = findViewById(R.id.btn_left)
            btnBack = findViewById(R.id.btn_back)
            timestamp = findViewById(R.id.timestamp)
            btnDelete = findViewById(R.id.btn_delete)
            viewPager = findViewById(R.id.viewPager)
            viewPager.setUserInputEnabled(true)

            // Safely retrieve extras
            val flashcardsId = intent.getIntExtra("flashcard_id", -1)
            val flashcardTitle = intent.getStringExtra("title")
            contentsList = intent.getStringArrayListExtra("cards")?.toMutableList() ?: mutableListOf()
            val flashcardsUpdatedAt = intent.getStringExtra("updated_at")

            // Set the timestamp text
            timestamp.text = flashcardsUpdatedAt

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

            btnCheck.setOnClickListener {
                // Ensure to update the current index in the contentsList
                val currentContent = contents.text.toString().trim()
                if (currentIndex < contentsList.size) {
                    contentsList[currentIndex] = currentContent // Update the list with the new content
                }
    
                //log flashcards id
                Log.d("ViewFlashcards", "Flashcards ID: $flashcardsId")
                if(flashcardsId != -1){
                    updateFlashcards(flashcardsId)
                }
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("showFlashcardFragment", true)
                startActivity(intent)
            }

            btnBack.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("showFlashcardFragment", true)
                startActivity(intent)
            }

            btnDelete.setOnClickListener {
                if(flashcardsId != -1){
                    deleteFlashcards(flashcardsId)
                }
            }
        }
    
        private fun updateFlashcards(flashcardsId: Int){
            val token = TokenManager.getToken()
    
            val updatedTitle = title.text.toString()
            val updatedContentList: MutableList<String> = contentsList.map { it.trim() }.toMutableList()
    
            if (updatedContentList.isEmpty()) {
                Toast.makeText(this, "Content list is empty.", Toast.LENGTH_SHORT).show()
                return
            }
            if (updatedTitle.isEmpty()) {
                Toast.makeText(this, "Title is empty.", Toast.LENGTH_SHORT).show()
                return
            }
    
            val flashcardRequest = UpdateFlashcards(
                title = updatedTitle,
                cards = updatedContentList.toMutableList() // This is your List<String> or MutableList<String>
            )
    
            Log.d("UpdateFlashcards", "Token: $token, Flashcards ID: $flashcardsId")
            Log.d("UpdateFlashcards", "Updated Title: $updatedTitle")
            Log.d("UpdateFlashcards", "Updated Content List: $updatedContentList")
    
            lifecycleScope.launch{
                try {
                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val response = withContext(Dispatchers.IO){
                        apiService.updateFlashcards("Bearer $token", flashcardsId, flashcardRequest)
                    }
                    if (response.isSuccessful){
                        Log.d("UpdateFlashcards", "Response Code: ${response.code()}")
                        Toast.makeText(this@ViewFlashcards, "Flashcards updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@ViewFlashcards, "Failed to update flashcards: ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.e("UpdateFlashcards", "Error: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@ViewFlashcards, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("UpdateFlashcards", "Error: ${e.message}", e)
                } catch (e: HttpException) {
                    Toast.makeText(this@ViewFlashcards, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("UpdateFlashcards", "HTTP Error: ${e.message()}", e)
                }
            }
        }
        private fun deleteFlashcards(flashcardsId: Int){
            val token = TokenManager.getToken()
            if (token == null) {
                Toast.makeText(this, "Authorization token missing", Toast.LENGTH_SHORT).show()
                return
            }
            lifecycleScope.launch {
                try {
                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val response = withContext(Dispatchers.IO) {
                        apiService.deleteFlashcards("Bearer $token", flashcardsId)
                        }
                        if (response.isSuccessful) {
                            Toast.makeText(this@ViewFlashcards, "Flashcards deleted successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@ViewFlashcards, HomeActivity::class.java)
                            intent.putExtra("showFlashcardFragment", true)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@ViewFlashcards, "Failed to delete flashcards: ${response.code()}", Toast.LENGTH_SHORT).show()
                            Log.e("DeleteFlashcards", "Error: ${response.code()}")
                        }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this@ViewFlashcards, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("DeleteFlashcards", "Error: ${e.message}", e)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    Toast.makeText(this@ViewFlashcards, "HTTP Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("DeleteFlashcards", "HTTP Error: ${e.message()}", e)
                }
            }
        }
        private fun addToContentsList(contents: String) {
            contentsList.add(contents)
        }
    }
