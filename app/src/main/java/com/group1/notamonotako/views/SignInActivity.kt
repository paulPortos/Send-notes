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

        TokenManager.init(this)

        if (TokenManager.isLoggedIn()) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        }

        setContentView(R.layout.activity_signin)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        SoundManager.initialize(this)

        progressBar = findViewById(R.id.progressBar)
        btnSignup = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnForgot = findViewById(R.id.btnForgotPassword)
        btnLoginNow = findViewById(R.id.btnSignInNow)
        mediaPlayer = MediaPlayer.create(this,R.raw.soundeffects)

        progressBar.visibility = View.INVISIBLE

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
                val response = apiService.login(loginRequest)

                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        TokenManager.saveToken(loginResponse.token)
                        Toast.makeText(this@SignInActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                } else {
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(this@SignInActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                    Log.e("Error", response.errorBody().toString())
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
