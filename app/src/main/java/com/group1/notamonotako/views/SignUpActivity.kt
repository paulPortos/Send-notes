package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.LinearGradient
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.group1.notamonotako.R
import com.group1.notamonotako.coroutines.signup.SignUpViewModel
import com.group1.notamonotako.coroutines.signup.SignUpViewModelFactory

class SignUpActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnLoginNow: Button
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp : AppCompatButton
    private lateinit var progressBar: ProgressBar
    private lateinit var mediaPlayer: MediaPlayer

    // Initialize ViewModel using the factory to provide ApiService
    private val signUpViewModel: SignUpViewModel by viewModels {
        SignUpViewModelFactory(RetrofitInstance.create(ApiService::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContentView(R.layout.activity_sign_up)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)
        btnLoginNow = findViewById(R.id.btnSignUpNow)
        btnSignIn = findViewById(R.id.btnSignIn)
        etEmail = findViewById(R.id.etEmail)
        mediaPlayer = MediaPlayer.create(this,R.raw.soundeffects)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE

        GradientText.setGradientText(btnSignUp,this)



        btnLoginNow.setOnClickListener {
            mediaPlayer.start()
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else if (password == confirmPassword) {
                if (username.length >= 5 && password.length >= 8) {
                    signUpViewModel.registerUser(email, username, password)
                    progressBar.visibility = View.VISIBLE
                } else if (username.length < 5) {
                    Toast.makeText(this@SignUpActivity, "Username must be at least 5 characters", Toast.LENGTH_SHORT).show()
                } else if (password.length < 8) {
                    Toast.makeText(this@SignUpActivity, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUpActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }

        }


        btnSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            progressBar.visibility = View.VISIBLE
            mediaPlayer.start()
        }



        // Observe registration result
        signUpViewModel.registrationResult.observe(this) { result ->
            progressBar.visibility = View.INVISIBLE
            result.onSuccess { response ->
                // Handle successful registration
                response?.let {
                    Toast.makeText(this@SignUpActivity, "Successfully signed up", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } ?: run {
                    Toast.makeText(this@SignUpActivity, "Response is null", Toast.LENGTH_SHORT).show()
                }
            }.onFailure { error ->
                // Handle registration errors
                Toast.makeText(this@SignUpActivity, "Invalid Email", Toast.LENGTH_SHORT).show()
                Log.d("TESTER", "Error: ${error.message}")
            }
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
    //block the back button of the phone
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

}
