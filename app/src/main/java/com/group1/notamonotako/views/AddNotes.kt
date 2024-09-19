package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.notes.PostnotesRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AddNotes : AppCompatActivity() {

    private lateinit var leftButton: ImageButton
    private lateinit var doneButton: ImageButton
    private lateinit var title: EditText
    private lateinit var contents: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        leftButton = findViewById(R.id.leftButton)
        doneButton = findViewById(R.id.doneButton)
        title  = findViewById(R.id.title)
        contents = findViewById(R.id.contents)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        leftButton.setOnClickListener {
            val intent = Intent(this@AddNotes, HomeActivity::class.java)
            startActivity(intent)
        }

        doneButton.setOnClickListener {
            val title = title.text.toString()
            val contents = contents.text.toString()
            CreateData(title, contents)
        }
    }



    private fun CreateData(title: String, contents: String) {
        lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val postNotes = PostnotesRequest(title = title, contents = contents, public = false, to_public = false)

            try {
                val response = apiService.createNote(postNotes)

                if (response.isSuccessful) {
                    val noteId = response.body()?.id
                    val dateString = response.body()?.updated_at // Assuming this is available in the response

                    if (noteId != null) {
                        // Navigate to Mynotes activity with created note's details
                        val intent = Intent(this@AddNotes, ViewMynotes::class.java).apply {
                            putExtra("title", title)
                            putExtra("contents", contents)
                            putExtra("note_id", noteId)
                            putExtra("date", dateString)
                        }

                        Toast.makeText(this@AddNotes, "Note created successfully", Toast.LENGTH_SHORT).show()

                        // Clear back stack and start Mynotes activity
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                        finish()
                    } else {
                        Toast.makeText(this@AddNotes, "Note created but ID is missing", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@AddNotes, "Failed to create note", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(this@AddNotes, "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@AddNotes, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
