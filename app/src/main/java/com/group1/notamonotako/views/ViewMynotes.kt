package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.api.AccountManager.getEmail
import com.group1.notamonotako.api.AccountManager.getUsername
import com.group1.notamonotako.api.requests_responses.admin.PostToAdmin
import com.group1.notamonotako.api.requests_responses.notes.UpdateNotes
import com.group1.notamonotako.api.requests_responses.notes.UpdateToPublicNotes
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
    private lateinit var flDelete : FrameLayout
    private lateinit var viewBlur : View
    private lateinit var btnCancel : AppCompatButton
    private lateinit var btnDelete : AppCompatButton
    private lateinit var btnCancelShare : AppCompatButton
    private lateinit var btnShare : AppCompatButton
    private lateinit var flShare : FrameLayout




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
        flDelete = findViewById(R.id.flDelete)
        viewBlur = findViewById(R.id.viewBlur)
        btnCancel = findViewById(R.id.btnCancel)
        btnDelete = findViewById(R.id.btnDelete)
        flShare = findViewById(R.id.flShare)
        btnCancelShare = findViewById(R.id.btnCancelShare)
        btnShare = findViewById(R.id.btnShare)

        val Intent = intent
        val Title = Intent.getStringExtra("title")
        val Contents = Intent.getStringExtra("contents")
        val DateString = Intent.getStringExtra("date")
        val Note_id = Intent.getIntExtra("note_id",-1)

        val recentTitle = Title.toString()
        val recentContents = Contents.toString()

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

        flDelete.visibility = View.GONE
        flShare.visibility = View.GONE
        viewBlur.visibility = View.GONE




        sharebtn.setOnClickListener{
            flShare.visibility = View.VISIBLE
            viewBlur.visibility = View.VISIBLE
            flShare.setOnTouchListener { _, _ -> true }
            viewBlur.setOnTouchListener { _, _ -> true }

        }
        btnCancelShare.setOnClickListener{
            flShare.visibility = View.GONE
            viewBlur.visibility = View.GONE
        }

        btnShare.setOnClickListener {
            val title = Title.toString()
            val creatorsUsername = getUsername().toString()
            val creatorsEmail = getEmail().toString()
            val contents = Content.text.toString()
            val public = false
            shareNote(Note_id, title, creatorsUsername, creatorsEmail, contents, public)
            setToPublicIntoTrue(Note_id)
        }



        deletebtn.setOnClickListener{
            flDelete.visibility = View.VISIBLE
            viewBlur.visibility = View.VISIBLE
            flDelete.setOnTouchListener { _, _ -> true }
            viewBlur.setOnTouchListener { _, _ -> true }
        }

        btnDelete.setOnClickListener {
            btnDelete.backgroundTintList= ContextCompat.getColorStateList(this, R.color.new_background_color)
            if (Note_id != -1) {
                DeleteNote(Note_id)
            }
        }

        btnCancel.setOnClickListener {
            flDelete.visibility = View.GONE
            viewBlur.visibility = View.GONE
        }

        btnback.setOnClickListener{
            val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
            intent.putExtra("showMyNotesFragment", true)
            startActivity(intent)
            finish()
        }


        UpdateNotes.setOnClickListener {
            val title = Title.toString()
            val contents = Content.text.toString()
            if (Note_id != -1) {
                if (recentTitle != title || recentContents != contents) {
                    update(Note_id)
                } else {
                    Toast.makeText(this, "No changes detected", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Note ID is missing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun shareNote(notesId: Int,title: String, creatorUsername: String, creatorEmail: String, contents: String, public: Boolean = false){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val postToAdmin = PostToAdmin(notesId, title, creatorUsername, creatorEmail, contents, public)
                val response = apiService.toAdmin(postToAdmin)

                if (response.isSuccessful) {
                    Toast.makeText(this@ViewMynotes, "Note shared successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ViewMynotes, HomeActivity::class.java)
                    intent.putExtra("showMyNotesFragment", true)
                    startActivity(intent)
                    finish()
                } else if(response.code() == 409){
                    Toast.makeText(this@ViewMynotes, "Note already shared and pending", Toast.LENGTH_SHORT).show()
                }else {
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
                val noteRequest = UpdateNotes(title = updatedTitle, contents = updatedContent, toPublic = false, public = false)

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