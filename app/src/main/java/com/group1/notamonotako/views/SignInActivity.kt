package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.signin.Login
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnForgot: Button
    private lateinit var btnLoginNow: Button
    private lateinit var progressBar : ProgressBar
    private lateinit var mediaPlayer: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize TokenManager
        TokenManager.init(this)

        // Check if the user is already logged in
        if (TokenManager.isLoggedIn()) {
            // If logged in, redirect to HomeActivity and finish this activity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close SignInActivity
            return  // Prevent further execution
        }

        // If not logged in, continue with login process
        setContentView(R.layout.activity_signin)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        // Initialize UI elements\
        SoundManager.initialize(this)

        progressBar = findViewById(R.id.progressBar)
        btnSignup = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnForgot = findViewById(R.id.btnForgotPassword)
        btnLoginNow = findViewById(R.id.btnSignInNow)
        mediaPlayer = MediaPlayer.create(this,R.raw.soundeffects)



        // Making the progressbar Invisible
        progressBar.visibility = View.INVISIBLE

        // Handle login button click
        btnLoginNow.setOnClickListener {
            mediaPlayer.start()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignInActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.visibility = View.VISIBLE
                loginUser(username, password)
            }
        }

        // Handle sign-up button click
        btnSignup.setOnClickListener {
            mediaPlayer.start()
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val loginRequest = Login(email = email, password = password)

            try {
                // Make the network call and get the response
                val response = apiService.login(loginRequest)

                if (response.isSuccessful) {
                    // If the response is successful, get the LoginResponse body
                    response.body()?.let { loginResponse ->
                        // Save the token
                        TokenManager.saveToken(loginResponse.token)

                        // Navigate to the HomeActivity
                        Toast.makeText(this@SignInActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()  // Close SignInActivity
                    }
                } else {
                    // Handle the error case (e.g., invalid credentials)
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@SignInActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@SignInActivity, "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@SignInActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("SignInActivity", e.message.toString())
            }
        }
    }

}
