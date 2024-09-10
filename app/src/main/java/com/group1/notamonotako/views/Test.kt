package com.group1.notamonotako.views

import ApiService

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.notes.Note
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Test : AppCompatActivity() {
    private lateinit var tester: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        Log.d("tester", TokenManager.getToken().toString())
        setContentView(R.layout.activity_test)

        tester = findViewById(R.id.tester)

        // Fetch and display notes
        fetchNotes()
    }

    private fun fetchNotes() {
        val apiService = RetrofitInstance.create(ApiService::class.java)

        // Make API call to fetch notes
        apiService.getNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    val notes = response.body()!!

                    val stringbuilder = StringBuilder()
                    for (datanotes in notes) {
                        stringbuilder.append("ID: ${datanotes.id}").append("\n")
                        stringbuilder.append("Title: ${datanotes.title}").append("\n")
                        stringbuilder.append("Contents: ${datanotes.contents}").append("\n")
                    }

                    tester.text = stringbuilder.toString()
                } else {
                    Toast.makeText(this@Test, "Failed to fetch notes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                Toast.makeText(this@Test, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("tester", t.message.toString())
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Exit the app when back is pressed
        finishAffinity()
    }
}