package com.group1.notamonotako.views

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.group1.notamonotako.R
import com.group1.notamonotako.databinding.ActivityHomeBinding
import com.group1.notamonotako.fragments.Flashcards
import com.group1.notamonotako.fragments.Home
import com.group1.notamonotako.fragments.MyFlashcards
import com.group1.notamonotako.fragments.MyNotes
import com.group1.notamonotako.fragments.Notes

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize binding first
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home()) // PARA MAG DIRECT AGAD SA HOME
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnhome -> replaceFragment(Home())
                R.id.btnnotes -> replaceFragment(MyNotes())
                R.id.btnflashcards -> replaceFragment(MyFlashcards())
                else -> {}
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }



}