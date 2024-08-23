package com.group1.notamonotako.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignup: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnForgot: Button
    private lateinit var btnLoginNow: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        btnSignup = findViewById(R.id.btnSignUp)
        etUsername = findViewById(R.id.usernameInput)
        etPassword = findViewById(R.id.passwordInput)
        btnForgot = findViewById(R.id.btnforgot)
        btnLoginNow = findViewById(R.id.btnloginnow)


        // LOGIN
        this.btnLoginNow.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            if (username == "") { // Empty Input
                Toast.makeText(this@SignInActivity, "Please Enter your Username", Toast.LENGTH_SHORT).show()
            } else if (password == "") //Empty Input
            { Toast.makeText(this@SignInActivity, "Please Enter your Password", Toast.LENGTH_SHORT).show()
            }
            else
            { Toast.makeText(this@SignInActivity, "You Log in Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ActivityHandler::class.java)
                startActivity(intent)
            }
        }


        // SIGN UP
        this.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}