package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.CommentsAdapter
import com.group1.notamonotako.api.AccountManager
import com.group1.notamonotako.api.requests_responses.comments.CommentPostRequest
import com.group1.notamonotako.api.requests_responses.comments.Comments
import kotlinx.coroutines.launch

class CommentActivity : AppCompatActivity() {
    private lateinit var etAddComment: EditText
    private lateinit var rvcomments: RecyclerView
    private lateinit var tvComments: TextView
    private lateinit var btnClose: ImageButton
    private lateinit var commentAdapter: CommentsAdapter
    private val data = mutableListOf<Comments>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        etAddComment = findViewById(R.id.etAddComment)
        rvcomments = findViewById(R.id.rvcomments)
        tvComments = findViewById(R.id.tvComments)
        btnClose = findViewById(R.id.btnClose)


        rvcomments.layoutManager = LinearLayoutManager(this)
        commentAdapter = CommentsAdapter(this, data)
        rvcomments.adapter = commentAdapter

        btnClose.setOnClickListener {
            // Navigate to CommentActivity directly
            val intent = Intent(this, ViewHome::class.java)

            // Pass data to the CommentActivity if needed (e.g., note_id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)



        }

        etAddComment.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                // Check if the drawableEnd was clicked
                if (event.rawX >= (etAddComment.right - etAddComment.compoundDrawables[2].bounds.width())) {
                    // Handle submission of the edit text content
                    submitComment(etAddComment.text.toString())
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun submitComment(comment: String) {
        if (comment.isNotBlank()) {
            val noteId = intent.getIntExtra("note_id", -1)


            val username = AccountManager.getUsername() ?: return // Get username dynamically
            val token = TokenManager.getToken() // Assume token is stored in TokenManager

            if (token == null) {
                Toast.makeText(this, "Authorization token missing", Toast.LENGTH_SHORT).show()
                return
            }

            lifecycleScope.launch {
                try {
                    val apiService = RetrofitInstance.create(ApiService::class.java)
                    val commentRequest = CommentPostRequest(
                        username = username,
                        notes_id = noteId,
                        comment = comment
                    )
                    val response = apiService.postComment("Bearer $token", commentRequest)

                    if (response.isSuccessful) {
                        // Handle successful comment submission
                        Toast.makeText(this@CommentActivity, "Comment Submitted", Toast.LENGTH_SHORT).show()
                        etAddComment.text?.clear()

                        // Add the new comment to the adapter
                        val newComment = Comments(username, comment)
                        commentAdapter.addComment(newComment) // Corrected line
                        rvcomments.scrollToPosition(commentAdapter.itemCount - 1) // Scroll to the new comment
                    } else {
                        // Log and handle API failure
                        val errorBody = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("CommentError", "Failed to submit comment: $errorBody")
                        Toast.makeText(this@CommentActivity, "Failed to submit comment: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    // Handle network or other errors
                    Log.e("CommentException", "Error: ${e.message}")
                    Toast.makeText(this@CommentActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Please input your comment", Toast.LENGTH_SHORT).show()
        }
    }
}
