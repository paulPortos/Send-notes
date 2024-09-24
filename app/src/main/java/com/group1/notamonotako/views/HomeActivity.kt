package com.group1.notamonotako.views

import TokenManager
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.group1.notamonotako.R
import com.group1.notamonotako.databinding.ActivityHomeBinding
import com.group1.notamonotako.fragments.Home
import com.group1.notamonotako.fragments.MyFlashcards
import com.group1.notamonotako.fragments.MyNotes

class HomeActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize TokenManager
        TokenManager.init(this)
        // Log the token to Logcat
        val token = TokenManager.getToken()
        Log.d("TOKENER", "Stored Token: $token")

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

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        // Check for intent extras to determine which fragment to show
        if (savedInstanceState == null) {
            val showMyNotesFragment = intent.getBooleanExtra("showMyNotesFragment", false)
            if (showMyNotesFragment) {
                replaceFragment(MyNotes(), "Notes")
                binding.bottomNavigationView.selectedItemId = R.id.btnnotes
            } else {
                replaceFragment(Home(),"Home")
                binding.bottomNavigationView.selectedItemId = R.id.btnhome
            }
        }

        // Set up bottom navigation listener
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnhome -> replaceFragment(Home(), "Home")
                R.id.btnnotes -> replaceFragment(MyNotes(), "Notes")
                R.id.btnflashcards -> replaceFragment(MyFlashcards(), "Flashcards")
                else -> {}
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentByTag(tag)

        // If the fragment is already displayed, do nothing
        if (currentFragment != null && currentFragment.isVisible) {
            return
        }

        val fragmentTransaction = fragmentManager.beginTransaction()

        // Replace the fragment and use a unique tag for each fragment
        fragmentTransaction.replace(R.id.frameLayout, fragment, tag)

        // Only add the fragment to the back stack if it's not "Home"
        if (tag != "Home") {
            fragmentTransaction.addToBackStack(tag)
        }

        fragmentTransaction.commit()

        // Show progress bar while the fragment is loading
        progressBar.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            progressBar.visibility = View.INVISIBLE
        }, 500)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragmentManager = supportFragmentManager
        val currentFragment = fragmentManager.findFragmentById(R.id.frameLayout)

        // If current fragment is Home and there's no back stack entry, exit the app
        if (currentFragment is Home && fragmentManager.backStackEntryCount == 0) {
            // Exit the app
            finishAffinity() // Closes the app and clears all activities
            return
        }

        // If there are fragments in the back stack, pop the stack
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()

            // Use a handler to wait until the fragment transaction is complete
            Handler(Looper.getMainLooper()).post {
                // Get the new current fragment after popping the stack
                val newCurrentFragment = fragmentManager.findFragmentById(R.id.frameLayout)

                // Update bottom navigation to reflect the new current fragment
                when (newCurrentFragment) {
                    is Home -> binding.bottomNavigationView.selectedItemId = R.id.btnhome
                    is MyNotes -> binding.bottomNavigationView.selectedItemId = R.id.btnnotes
                    is MyFlashcards -> binding.bottomNavigationView.selectedItemId = R.id.btnflashcards
                }
            }
        } else {
            // If there's no more back stack, finish the activity (exit the app)
            finish()
        }
    }

}