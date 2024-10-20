package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.notes.PostnotesRequest
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ViewSendNotes : AppCompatActivity() {
    private lateinit var btncopy : AppCompatButton
    private lateinit var btndelete : ImageButton
    private lateinit var btnback : ImageButton
    private lateinit var tvSentBy : TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvContents:  TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_send_notes)

        btnback = findViewById(R.id.btnback)
        btndelete = findViewById(R.id.btndelete)
        btncopy = findViewById(R.id.btncopy)
        tvSentBy = findViewById(R.id.sent_by)
        tvTitle = findViewById(R.id.Title)
        tvContents = findViewById(R.id.Contents)


        val intent = intent
        val id = intent.getIntExtra("id", -1)
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val noteId = intent.getIntExtra("note_id",-1)
        val sentBy = intent.getStringExtra("sent_by")

        this.tvTitle.text = title
        this.tvContents.text = contents
        this.tvSentBy.text = "Sent by: $sentBy"

        btnback.setOnClickListener {
            finish()
        }

        btndelete.setOnClickListener {
            deleteSentNotes(id, false)
        }

        btncopy.setOnClickListener{
            val titleString = tvTitle.text.toString()
            val contentsString = tvContents.text.toString()
            val publicDefaultValue = false
            val toPublicDefaultValue = false
            copyNotes(titleString, contentsString, publicDefaultValue, toPublicDefaultValue, id)
        }
    }

    private fun copyNotes(title: String, contents: String, public: Boolean, toPublic: Boolean, id: Int){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val notesRequest = PostnotesRequest(title, contents, public, toPublic)
                val response = apiService.createNote(notesRequest)
                if (response.isSuccessful) {
                    deleteSentNotes(id, true)
                } else {
                    Log.e("ViewSendNotes", "Failed to create note: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViewSendNotes", "Exception: ${e.message}")
            }
        }
    }

    private fun deleteSentNotes(id: Int, copy: Boolean){
        lifecycleScope.launch {
            try {
                val token = TokenManager.getToken()
                if (token != null) {
                    val bearerToken = "Bearer $token"
                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val response = apiService.deleteSentNote(bearerToken, id)
                    if (response.isSuccessful) {
                        if (copy) {
                            Toast.makeText(this@ViewSendNotes, "Note copied successfully", Toast.LENGTH_SHORT).show()
                            Log.d("ViewSendNotes", "Note copied successfully")
                            val intent = Intent(this@ViewSendNotes, HomeActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@ViewSendNotes, "Note deleted successfully", Toast.LENGTH_SHORT).show()
                            Log.d("ViewSendNotes", "Note deleted successfully")
                            val intent = Intent(this@ViewSendNotes, SendNotesActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.e("ViewSendNotes", "Failed to delete note: ${response.code()}")
                        Toast.makeText(this@ViewSendNotes, "Failed to delete note", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@ViewSendNotes, "Token not found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("ViewSendNotes", "Exception: ${e.message}")
                Toast.makeText(this@ViewSendNotes, "Failed to delete note", Toast.LENGTH_SHORT).show()
            } catch (e: HttpException) {
                Log.e("ViewSendNotes", "HTTP Exception: ${e.message}")
                Toast.makeText(this@ViewSendNotes, "Failed to delete note", Toast.LENGTH_SHORT).show()
            }
        }
    }
}