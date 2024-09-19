package com.group1.notamonotako.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.group1.notamonotako.R

class AddFlashcards : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnRight: ImageButton
    private lateinit var btnLeft : ImageButton
    private lateinit var btnBack: ImageButton
    private lateinit var title : EditText
    private lateinit var contents : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards) // Use your activity layout

        // Lock orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        viewPager = findViewById(R.id.viewPager)
        btnRight = findViewById(R.id.btn_right)
        btnBack = findViewById(R.id.btn_back)
        btnLeft = findViewById(R.id.btn_left)
        title = findViewById(R.id.title)
        contents = findViewById(R.id.contents)



        viewPager.setUserInputEnabled(false)
        btnLeft.setOnClickListener {


        }

        btnRight.setOnClickListener {

        }

        btnBack.setOnClickListener {
           val intent = Intent(this@AddFlashcards, HomeActivity::class.java)
            startActivity(intent)
        }
    }




}
