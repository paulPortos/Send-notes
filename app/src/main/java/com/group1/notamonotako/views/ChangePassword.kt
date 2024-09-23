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

class ChangePassword : AppCompatActivity() {
    private lateinit var btnBack : ImageButton
    private lateinit var etNewPassword : EditText
    private lateinit var etConfirmNewPassword : EditText
    private lateinit var etOldPassword: EditText
    private lateinit var btnConfirmResetPassword : AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        btnBack = findViewById(R.id.btnBack)
        etOldPassword = findViewById(R.id.etOldPassword)
        etNewPassword = findViewById(R.id.etNewPassword)
        etConfirmNewPassword = findViewById(R.id.etConfirmNewPassword)
        btnConfirmResetPassword = findViewById(R.id.btnConfirmResetPassword)

        btnBack.setOnClickListener {
            val intent = Intent(this@ChangePassword, SettingsActivity::class.java)
            startActivity(intent)
        }

        btnConfirmResetPassword.setOnClickListener {
            val intent = Intent(this@ChangePassword, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}