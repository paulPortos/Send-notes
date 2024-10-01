package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.api.AccountManager.getEmail
import com.group1.notamonotako.api.AccountManager.getUsername
import com.group1.notamonotako.api.requests_responses.admin.postToAdmin
import com.group1.notamonotako.api.requests_responses.notes.UpdateNotes
import com.group1.notamonotako.api.requests_responses.notes.UpdateToPublicNotes
import com.group1.notamonotako.fragments.MyFlashcards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class ViewMynotes : AppCompatActivity() {

    private lateinit var Title: EditText
    private lateinit var Content: EditText
    private lateinit var Date: TextView
    private lateinit var deletebtn : ImageButton
    private lateinit var UpdateNotes : ImageView
    private lateinit var btnback : ImageButton
    private lateinit var sharebtn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mynotes)

        Date = findViewById(R.id.date)
        Title = findViewById(R.id.Title)
        Content = findViewById(R.id.Contents)
        deletebtn = findViewById(R.id.deletebtn)
        btnback = findViewById(R.id.btnback)
        UpdateNotes = findViewById(R.id.Update_Notes)
        sharebtn = findViewById(R.id.sharebtn)

        val Intent = intent
        val Title = Intent.getStringExtra("title")
        val Contents = Intent.getStringExtra("contents")
        val DateString = Intent.getStringExtra("date")
        val Note_id = Intent.getIntExtra("note_id",-1)

        this.Title.setText(Title)
        this.Content.setText(Contents)

        if (DateString != null) {
            // Define the input and output date formats
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            try {
                val date = inputFormat.parse(DateString)
                val formattedDate = date?.let { outputFormat.format(it) }
                this.Date.text = formattedDate
            } catch (e: Exception) {
                // Handle parsing exception
                e.printStackTrace()
                this.Date.text = "Invalid date"
            }
        }
        sharebtn.setOnClickListener{
            val title = Title.toString()
            val creatorsUsername = getUsername().toString()
            val creatorsEmail = getEmail().toString()
            val contents = Content.text.toString()
            val public = false

            shareNote(title, creatorsUsername, creatorsEmail, contents, public)
            setToPublicIntoTrue(Note_id)
        }

        btnback.setOnClickListener{
            val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
            startActivity(intent)
        }


        deletebtn.setOnClickListener{
            deletebtn.backgroundTintList= ContextCompat.getColorStateList(this, R.color.new_background_color)
            if (Note_id != -1) {
                DeleteNote(Note_id)
            }
        }
        UpdateNotes.setOnClickListener {
            if (Note_id != -1) {
                update(Note_id)
            }
        }
    }

    private fun shareNote(title: String, creatorUsername: String, creatorEmail: String, contents: String,  public: Boolean){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val postToAdmin = postToAdmin(title, creatorUsername, creatorEmail, contents, public)
                val response = apiService.toAdmin(postToAdmin)

                if (response.isSuccessful) {
                    Toast.makeText(this@ViewMynotes, "Note shared successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@ViewMynotes, "Failed to share note: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("ShareNote", "Error: ${response.code()}, Message: ${response.message()}")
                }
            } catch (e: HttpException){
                Toast.makeText(this@ViewMynotes, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(this@ViewMynotes, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun DeleteNote(noteId: Int) {
        val token = TokenManager.getToken()
        if (token == null) {
            Toast.makeText(this, "Authorization token missing", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("DeleteNote", "Token: $token, Note ID: $noteId")

        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.deleteNote("Bearer $token", noteId)  // Ensure token is correctly formatted
                }

                if (response.isSuccessful) {
                    Toast.makeText(this@ViewMynotes, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
                    intent.putExtra("showMyNotesFragment", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()  // Finish Mynotes activity to avoid returning to it
                } else {
                    Log.e("DeleteNote", "Error: ${response.code()}, Message: ${response.message()}")
                    Toast.makeText(this@ViewMynotes, "Failed to delete note: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@ViewMynotes, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun update(noteId: Int) {
        val token = TokenManager.getToken()
        if (token == null) {
            Toast.makeText(this, "Authorization token missing", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedTitle = Title.text.toString()
        val updatedContent = Content.text.toString()

        if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
            Toast.makeText(this, "Title or content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("UpdateNote", "Token: $token, Note ID: $noteId")

        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val noteRequest = UpdateNotes(title = updatedTitle, contents = updatedContent)

                val response = withContext(Dispatchers.IO) {
                    apiService.updateNote("Bearer $token", noteId, noteRequest)  // Pass the updated note
                }
                if (response.isSuccessful) {
                    Toast.makeText(this@ViewMynotes, "Note updated successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
                    intent.putExtra("showMyNotesFragment", true)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()  // Finish Mynotes activity to avoid returning to it
                } else {
                    Toast.makeText(this@ViewMynotes, "Failed to update note: ${response.code()}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@ViewMynotes, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setToPublicIntoTrue(noteId: Int){
        val token = TokenManager.getToken()
        if (token == null) {
            Toast.makeText(this, "Authorization token missing", Toast.LENGTH_SHORT).show()
            return
        }
        val updatedTitle = Title.text.toString()
        val updatedContent = Content.text.toString()

        val toPublic = true
        if (updatedTitle.isEmpty() || updatedContent.isEmpty()) {
            Toast.makeText(this, "Title or content cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val noteRequest = UpdateToPublicNotes(title = updatedTitle, contents = updatedContent, toPublic = toPublic)
                val response = withContext(Dispatchers.IO) {
                    apiService.updateNotesToPublic("Bearer $token", noteId, noteRequest)
                }
            } catch (e: Exception){
                e.printStackTrace()
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