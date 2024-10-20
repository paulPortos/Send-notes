package com.group1.notamonotako.views

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.group1.notamonotako.R

class ViewSendNotes : AppCompatActivity() {
    private lateinit var btncopy : AppCompatButton
    private lateinit var btndelete : ImageButton
    private lateinit var btnback : ImageButton
    private lateinit var tvSentBy : TextView
    private lateinit var tvTitle: TextView
    private lateinit var tvContents:  TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_send_notes)

        btnback = findViewById(R.id.btnback)
        btndelete = findViewById(R.id.btndelete)
        btncopy = findViewById(R.id.btncopy)
        tvSentBy = findViewById(R.id.sent_by)
        tvTitle = findViewById(R.id.Title)
        tvContents = findViewById(R.id.Contents)
        val intent = intent
        val title = intent.getStringExtra("title")
        val contents = intent.getStringExtra("contents")
        val noteId = intent.getIntExtra("note_id",-1)
        val sentBy = intent.getStringExtra("sent_by")

        this.tvTitle.text = title
        this.tvContents.text = contents
        this.tvSentBy.text = "Sent by: " + sentBy




        btnback.setOnClickListener {
            finish()
        }

    }

}