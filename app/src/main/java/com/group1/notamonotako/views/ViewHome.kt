package com.group1.notamonotako.views

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.LinearLayout
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_home)

        btnLike = findViewById(R.id.btnLike)
        btnDisLike = findViewById(R.id.btnDisLike)
        btnComment = findViewById(R.id.btnComment)
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