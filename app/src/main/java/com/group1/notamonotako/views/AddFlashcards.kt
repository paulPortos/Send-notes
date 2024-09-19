package com.group1.notamonotako.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.EditTextFragmentAdapter

class AddFlashcards : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnRight: ImageButton
    private lateinit var btnLeft : ImageButton
    private lateinit var btnBack: ImageButton
    private val dataList = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: EditTextFragmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcards) // Use your activity layout

        // Lock orientation
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Initialize views
        viewPager = findViewById(R.id.viewPager)
        btnRight = findViewById(R.id.btn_right)
        btnBack = findViewById(R.id.btn_back)
        btnLeft = findViewById(R.id.btn_left)

        // Initialize data list with one blank card
        dataList.add(Pair("", ""))

        adapter = EditTextFragmentAdapter(this, dataList)
        viewPager.adapter = adapter
        viewPager.setUserInputEnabled(false)
        btnLeft.setOnClickListener {
            saveCurrentPageData()
            val currentItem =viewPager.currentItem

            if(currentItem>0){
                viewPager.currentItem = currentItem -1
            }
        }

        btnRight.setOnClickListener {
            saveCurrentPageData()
            val currentItem = viewPager.currentItem
            // Move to the next page
            if (currentItem == adapter.itemCount - 1) {
                // If it's the last page, add a new blank card
                dataList.add(Pair("", ""))
                adapter.notifyItemInserted(dataList.size - 1)
            }
            viewPager.currentItem = currentItem + 1
        }

        btnBack.setOnClickListener {
           val intent = Intent(this@AddFlashcards, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    private fun saveCurrentPageData() {
        val currentItem = viewPager.currentItem
        val fragment = adapter.getFragment(currentItem)
        if (fragment != null) {
            dataList[currentItem] = Pair(fragment.getTitle(), fragment.getContents())
        }
    }
}
