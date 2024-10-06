package com.group1.notamonotako.views

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R

class ViewHome : AppCompatActivity() {
    private lateinit var btnLike : ImageButton
    private lateinit var btnDisLike : ImageButton
    private lateinit var btnComment : ImageButton
    private lateinit var flComment : FrameLayout
    private lateinit var btnClose : ImageButton
    private lateinit var tvTitle : TextView
    private lateinit var tvContents : TextView
    private lateinit var tvDate : TextView
    private lateinit var etAddComment : EditText
    private lateinit var rvcomments : RecyclerView
    private lateinit var tvComments : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)

        btnLike = findViewById(R.id.btnLike)
        btnDisLike = findViewById(R.id.btnDisLike)
        btnComment = findViewById(R.id.btnComment)
        tvTitle = findViewById(R.id.tvTitle)
        tvContents = findViewById(R.id.tvContents)
        tvDate = findViewById(R.id.tvDate)
        etAddComment = findViewById(R.id.etAddComment)
        rvcomments = findViewById(R.id.rvcomments)
        tvComments = findViewById(R.id.tvComments)

        val adminId = intent.getIntExtra("admin_id", -1)
        val noteId = intent.getIntExtra("note_id", -1)
        val title = intent.getStringExtra("title")
        val creatorUsername = intent.getStringExtra("creator_username")
        val creatorEmail = intent.getStringExtra("creator_email")
        val contents = intent.getStringExtra("contents")
        val public = intent.getBooleanExtra("public", false)
        val updatedAt = intent.getStringExtra("updated_at")

        //log all
        Log.d("ViewHome", "adminId: $adminId, noteId: $noteId, title: $title, creatorUsername: $creatorUsername, creatorEmail: $creatorEmail, contents: $contents, public: $public, updatedAt: $updatedAt")
        tvTitle.text = title ?: "No title"
        tvContents.text = contents ?: "No contents"
        tvDate.text = updatedAt ?: "No date"
        flComment = findViewById(R.id.flComment)
        btnClose = findViewById(R.id.btnClose)

        flComment.visibility - View.GONE
        var isLiked = false
        btnLike.setOnClickListener {
            if (isLiked) {
                // If already liked, remove the color and reset
                btnLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                btnDisLike.isClickable = true // Re-enable btnDisLike
            } else {
                // If not liked, apply the color and disable btnDisLike
                btnLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.mixcolor))
                btnDisLike.isClickable = false // Disable btnDisLike
            }
            isLiked = !isLiked

        }
        btnDisLike.setOnClickListener {
            if (isLiked) {
                // If already liked, remove the color and reset
                btnDisLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
                btnLike.isClickable = true
            } else {
                // If not liked, apply the color and disable btnDisLike
                btnDisLike.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.mixcolor))
                btnLike.isClickable = false
            }
            isLiked = !isLiked

        }
        
        btnComment.setOnClickListener {
            btnComment.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.mixcolor))
            // Show the comment frame with a slide-up animation
            flComment.visibility = View.VISIBLE
            flComment.animate().translationY(0f).setDuration(300).start()
        }

        btnClose.setOnClickListener {
            // Hide the comment frame with a slide-down animation
            btnComment.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
            flComment.animate().translationY(flComment.height.toFloat()).setDuration(300).withEndAction {
                flComment.visibility = View.GONE // Set visibility to GONE after the animation
            }.start()
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
    // Function to handle comment submission

    private fun submitComment(comment: String) {
        if (comment.isNotBlank()) {
            Toast.makeText(this, "Comment Submitted", Toast.LENGTH_SHORT).show()
            etAddComment.text?.clear()
        }
        else{
            Toast.makeText(this, "Please Input Your Comment", Toast.LENGTH_SHORT).show()
        }
    }


}