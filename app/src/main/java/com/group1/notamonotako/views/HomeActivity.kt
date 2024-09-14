package com.group1.notamonotako.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.group1.notamonotako.R
import com.group1.notamonotako.databinding.ActivityHomeBinding
import com.group1.notamonotako.fragments.Home
import com.group1.notamonotako.fragments.MyFlashcards
import com.group1.notamonotako.fragments.MyNotes

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize TokenManager
        TokenManager.init(this)

        // Check if the user is logged in
        if (!TokenManager.isLoggedIn()) {
            // If not logged in, redirect to SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()  // Close the HomeActivity
            return  // Prevent further execution
        }

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Initialize binding first
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the default fragment
        replaceFragment(Home())

        // Set up bottom navigation listener
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