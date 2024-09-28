package com.group1.notamonotako.views

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class ForgotPassword_ResetPassword : AppCompatActivity() {
    private lateinit var btnBack : ImageButton
    private lateinit var etPassword :EditText
    private lateinit var etConfirmPassword : EditText
    private lateinit var btnConfirmResetPassword : AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_reset_password)

        btnBack = findViewById( R.id.btnBack)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById( R.id.etConfirmPassword)
        btnConfirmResetPassword = findViewById(R.id.btnConfirmResetPassword)

        GradientText.setGradientText(btnConfirmResetPassword,this)

        btnBack.setOnClickListener {
            val intent = Intent(this@ForgotPassword_ResetPassword, SignInActivity::class.java)
            startActivity(intent)
        }

        btnConfirmResetPassword.setOnClickListener {
            val intent = Intent(this@ForgotPassword_ResetPassword, SignInActivity::class.java)
            startActivity(intent)
        }


    }
}