package com.group1.notamonotako.views

import ApiService
import TokenManager
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.AccountManager
import com.group1.notamonotako.api.requests_responses.comments.CommentPostRequest
import com.group1.notamonotako.api.requests_responses.public_notes.getPublicNotes
import kotlinx.coroutines.launch

class ViewHome : AppCompatActivity() {
    private lateinit var btnLike : ImageButton
    private lateinit var btnDisLike : ImageButton
    private lateinit var btnComment : ImageButton
    private lateinit var flComment : FrameLayout
    private lateinit var btnback : ImageButton
    private lateinit var tvTitle : TextView
    private lateinit var tvContents : TextView
    private lateinit var tvDate : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)
        AccountManager.init(this)
        TokenManager.init(this)

        btnback = findViewById(R.id.btnback)
        btnLike = findViewById(R.id.btnLike)
        btnDisLike = findViewById(R.id.btnDisLike)
        btnComment = findViewById(R.id.btnComment)
        tvTitle = findViewById(R.id.tvTitle)
        tvContents = findViewById(R.id.tvContents)
        tvDate = findViewById(R.id.tvDate)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        val noteId = intent.getIntExtra("note_id", -1)
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val public = intent.getBooleanExtra("public", false)
        val updatedAt = intent.getStringExtra("updated_at")

        //log all
        Log.d("ViewHometester", "noteId: $noteId, title: $title,  contents: $contents, public: $public, updatedAt: $updatedAt")
        tvTitle.text = title ?: "No title"
        tvContents.text = contents ?: "No contents"
        tvDate.text = updatedAt ?: "No date"


        btnback.setOnClickListener{
            finish()
        }



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
            // Change the button tint back to white
            btnComment.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))

            // Navigate to CommentActivity directly
            val intent = Intent(this, CommentActivity::class.java)

            // Pass data to the CommentActivity if needed (e.g., note_id)
            intent.putExtra("note_id", noteId)  // Example of passing note_id

            startActivity(intent)

            // Add a transition animation between activities
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }



    }

}




