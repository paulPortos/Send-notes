package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class ViewHome : AppCompatActivity() {
    private lateinit var btnLike : ImageButton
    private lateinit var btnDisLike : ImageButton
    private lateinit var btnComment : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)

        btnLike = findViewById(R.id.btnLike)
        btnDisLike = findViewById(R.id.btnDisLike)
        btnComment = findViewById(R.id.btnComment)
        btnLike.setOnClickListener {

        }



//        btnComment.setOnClickListener {
//            // Get the comment layout
//            val commentLayout = findViewById<LinearLayout>(R.id.commentLayout)
//
//            // Check if the comment layout is already visible
//            if (commentLayout.visibility == View.GONE) {
//                // Show the comment layout with an animation
//                commentLayout.visibility = View.VISIBLE
//                commentLayout.translationY = commentLayout.height.toFloat() // Start from below
//                commentLayout.animate()
//                    .translationY(0f) // Move to original position
//                    .setDuration(300)
//                    .start()
//            } else {
//                // Hide the comment layout with an animation
//                commentLayout.animate()
//                    .translationY(commentLayout.height.toFloat()) // Move down
//                    .setDuration(300)
//                    .withEndAction {
//                        commentLayout.visibility = View.GONE // Set visibility after animation
//                    }
//                    .start()
//            }
//        }
    }
}