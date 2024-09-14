package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import retrofit2.Callback
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.signup.RegisterRequests
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponses
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private  lateinit var  etEmail : EditText
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnLoginNow: Button
    private lateinit var btnSignIn: Button
    private lateinit var progressBar : ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        setContentView(R.layout.activity_sign_up)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.etUsername = findViewById(R.id.etUsername)
        this.etPassword = findViewById(R.id.etPassword)
        this.etConfirmPassword = findViewById(R.id.etConfirmPassword)
        this.btnLoginNow = findViewById(R.id.btnSignUpNow)
        this.btnSignIn = findViewById(R.id.btnSignIn)
        this.etEmail = findViewById(R.id.etEmail)

        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE


        this.btnLoginNow.setOnClickListener {
            val email = etEmail.text.toString()
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else if (password == confirmPassword) {
                if (username.length >= 3 && password.length >= 5) {
                    registerUser(email, username, password)
                    progressBar.visibility = View.VISIBLE
                } else if (username.length < 3){
                    Toast.makeText(this@SignUpActivity, "Username must be at least 3 characters", Toast.LENGTH_SHORT).show()
                } else if (password.length < 5){
                    Toast.makeText(this@SignUpActivity, "Password must be at least 5 characters", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@SignUpActivity, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }

        this.btnSignIn.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            progressBar.visibility = View.VISIBLE
        }
    }
    private fun registerUser(email:String,username: String, password: String) {
        // Create API service instance
        val apiService = RetrofitInstance.create(ApiService::class.java)

        // Create request body
        val registrationRequest = RegisterRequests(email = email, username = username, password = password)

        // Make the API call
        val call = apiService.register(registrationRequest)

        // Enqueue the API call to be executed asynchronously
        call.enqueue(object : Callback<RegistrationResponses> {
            override fun onResponse(call: Call<RegistrationResponses>, response: Response<RegistrationResponses>) {
                when {
                    response.code() == 409 -> {
                        // Handle conflict error (e.g., user already exists)
                        Toast.makeText(this@SignUpActivity, "User already exists", Toast.LENGTH_SHORT).show()
                    }
                    response.isSuccessful -> {
                        // Registration successful, navigate to sign-in activity
                        Toast.makeText(this@SignUpActivity, "Successfully signed up", Toast.LENGTH_SHORT).show()

                        // You can also save the token here if the response contains it
                        val registrationResponse = response.body()
                        registrationResponse?.token?.let { token ->
                            TokenManager.saveToken(token) // Save the token
                        }

                        // Navigate to the login activity
                        val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                    else -> {
                        // Handle other errors with detailed error message
                        progressBar.visibility = View.INVISIBLE
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Toast.makeText(this@SignUpActivity, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(p0: Call<RegistrationResponses>, t: Throwable) {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@SignUpActivity, "Network error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}