package com.group1.notamonotako.views

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Callback
import androidx.appcompat.app.AppCompatActivity

import com.group1.notamonotako.R
import com.group1.notamonotako.api.ApiClient
import com.group1.notamonotako.api.ApiService
import com.group1.notamonotako.api.requests_responses.signup.RegistrationRequest
import com.group1.notamonotako.api.requests_responses.signup.RegistrationResponse
import retrofit2.Call
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnLoginNow: Button
    private lateinit var btnSignIn: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        this.etUsername = findViewById(R.id.etUsername)
        this.etPassword = findViewById(R.id.etPassword)
        this.etConfirmPassword = findViewById(R.id.etConfirmPassword)
        this.btnLoginNow = findViewById(R.id.btnSignUpNow)
        this.btnSignIn = findViewById(R.id.btnSignIn)

        this.btnLoginNow.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else if (password == confirmPassword) {
                if (username.length >= 3 && password.length >= 5) {
                    registerUser(username, password)
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
        }
    }
    private fun registerUser(username: String, password: String){
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val registrationRequest = RegistrationRequest(username = username, password = password)
        val call = apiService.signUpUser(registrationRequest)

        call.enqueue(object : Callback<RegistrationResponse> {
            override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                if (response.code() == 409) {
                    Toast.makeText(this@SignUpActivity, "User already exists", Toast.LENGTH_SHORT).show()
                } else if (response.isSuccessful){
                    Toast.makeText(this@SignUpActivity, "Successfully signed up", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignUpActivity, SignInActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this@SignUpActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<RegistrationResponse>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Network error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}