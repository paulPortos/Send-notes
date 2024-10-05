package com.group1.notamonotako.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)

        btnLike = findViewById(R.id.btnLike)
        btnDisLike = findViewById(R.id.btnDisLike)
        btnComment = findViewById(R.id.btnComment)
        tvTitle = findViewById(R.id.tvTitle)
        tvContents = findViewById(R.id.tvContents)
        tvDate = findViewById(R.id.tvDate)

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
        btnLike.setOnClickListener {

        }
        
        btnComment.setOnClickListener {
            // Show the comment frame with a slide-up animation
            flComment.visibility = View.VISIBLE
            flComment.animate().translationY(0f).setDuration(300).start()
        }

        btnClose.setOnClickListener {
            // Hide the comment frame with a slide-down animation
            flComment.animate().translationY(flComment.height.toFloat()).setDuration(300).withEndAction {
                flComment.visibility = View.GONE // Set visibility to GONE after the animation
            }.start()
        }
    }

}