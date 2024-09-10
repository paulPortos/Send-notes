package com.group1.notamonotako.views

import ApiService
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.signin.Login
import com.group1.notamonotako.api.requests_responses.signin.LoginResponse

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnForgot: Button
    private lateinit var btnLoginNow: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)

        if (TokenManager.isLoggedIn()) {
            // Redirect to the main activity if the user is logged in
            val intent = Intent(this, Test::class.java)
            startActivity(intent)
        } else {
            // Redirect to the SignInActivity if the user is not logged in
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_signin)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnSignup = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnForgot = findViewById(R.id.btnForgotPassword)
        btnLoginNow = findViewById(R.id.btnSignInNow)

        // LOGIN
        this.btnLoginNow.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignInActivity, "Fill up all fields", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(username, password)
                       }
        }

        // SIGN UP
        this.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loginUser(username: String, password: String){
        val apiService = RetrofitInstance.create(ApiService::class.java)
        val loginRequest = Login(username = username, password = password)
        val call = apiService.login(loginRequest)


        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    authResponse?.let {
                        // Save the token
                        TokenManager.saveToken(it.token)
                        // Navigate to the next screen
                        Toast.makeText(this@SignInActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, Test::class.java)
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@SignInActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "Network error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("tester", t.message.toString())
            }
        })









       /* call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    if(token != null){
                        tokenManager = TokenManager(this@SignInActivity)
                        tokenManager.saveToken(token)
                        Log.d("Token: ", token)
                        Toast.makeText(this@SignInActivity, "Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else if (response.code() == 201){
                        Toast.makeText(this@SignInActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SignInActivity, "Error: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(p0: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@SignInActivity, "Network error occurred: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })*/
    }

}