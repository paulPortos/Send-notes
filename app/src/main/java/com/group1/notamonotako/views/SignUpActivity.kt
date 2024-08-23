package com.group1.notamonotako.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.group1.notamonotako.R

class SignUpActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnLoginNow: Button
    private lateinit var btnSignIn: Button


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        etUsername = findViewById(R.id.etusername)
        etPassword = findViewById(R.id.Password)
        etConfirmPassword = findViewById(R.id.confirmedpasss)
        btnLoginNow = findViewById(R.id.btnloginnow)
        btnSignIn = findViewById(R.id.btnSignIn)


        this.btnLoginNow.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()
            val confirmpassword = etConfirmPassword.text.toString()
            if (username == "") { // Empty Input
                Toast.makeText(this@SignUpActivity, "Please Enter your Username", Toast.LENGTH_SHORT).show()
            } else if (password == "") //Empty Input

            { Toast.makeText(this@SignUpActivity, "Please Enter your Password", Toast.LENGTH_SHORT).show()

            }
            else if (confirmpassword == "") //Empty Input
            { Toast.makeText(this@SignUpActivity, "Please Enter your Confirm Password", Toast.LENGTH_SHORT).show()

            }
            else if (password == confirmpassword)
            { Toast.makeText(this@SignUpActivity, "Account has been successfully created", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)

            }
            else
            { Toast.makeText(this@SignUpActivity, "Confirm Password is not match to Password", Toast.LENGTH_SHORT).show()
            }

        }

        this.btnSignIn.setOnClickListener{
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }



    }
}