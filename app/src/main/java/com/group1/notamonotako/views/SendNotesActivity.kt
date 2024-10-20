package com.group1.notamonotako.views

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import retrofit2.Response

class SendNotesActivity : AppCompatActivity() {
    private lateinit var btnClose : ImageButton
    private lateinit var rvSendNotes: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_mail)

        btnClose = findViewById(R.id.btnClose)
        rvSendNotes = findViewById(R.id.rvSendNotes)

        btnClose.setOnClickListener {
            finish()
        }
    }
}