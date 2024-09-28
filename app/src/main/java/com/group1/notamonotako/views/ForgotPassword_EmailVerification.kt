package com.group1.notamonotako.views

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import com.group1.notamonotako.R

class ForgotPassword_EmailVerification : AppCompatActivity() {
    private lateinit var btnBack : ImageButton
    private lateinit var etEmail : EditText
    private lateinit var etOTP : EditText
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var btnGetOTP : AppCompatButton
    private lateinit var btnConfirmVerification :AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_email_verification)

        etEmail = findViewById(R.id.etEmail)
        etOTP = findViewById(R.id.etOTP)
        textInputLayout = findViewById(R.id.textInputLayout)
        btnGetOTP = findViewById(R.id.btnGetOTP)
        btnBack = findViewById( R.id.btnBack)
        btnConfirmVerification = findViewById(R.id.btnConfirmVerification)

        textInputLayout.visibility = View.INVISIBLE
        GradientText.setGradientText(btnGetOTP,this)
        GradientText.setGradientText(btnConfirmVerification,this)

        btnGetOTP.setOnClickListener {
            textInputLayout.visibility = View.VISIBLE
            OTPcountdown()
        }

        btnConfirmVerification.setOnClickListener{
            val intent = Intent(this@ForgotPassword_EmailVerification, ForgotPassword_ResetPassword::class.java)
            startActivity(intent)

        }

        btnBack.setOnClickListener {
            val intent = Intent(this@ForgotPassword_EmailVerification, SignInActivity::class.java)
            startActivity(intent)
        }

    }
    private fun OTPcountdown(){
        btnGetOTP.isEnabled = false
        object : CountDownTimer(60000,1000){
            override fun onTick(millisUntilFinished : Long) {
                btnGetOTP.text = "${millisUntilFinished / 1000} seconds "
            }
            override fun onFinish() {
                btnGetOTP.text = "GET OTP "
                btnGetOTP.isEnabled = true
                textInputLayout.visibility = View.INVISIBLE
            }
        }.start()

    }
}