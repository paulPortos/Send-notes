package com.group1.notamonotako.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import retrofit2.Callback
import com.group1.notamonotako.api.ApiClient
import com.group1.notamonotako.api.ApiService
import retrofit2.Call
import retrofit2.Response

class AdminActivity : AppCompatActivity() {
    lateinit var logout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        logout = findViewById(R.id.logout_btn)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Ensure you have an Adapter class defined elsewhere
        val adapter = Adapter(example())
        recyclerView.adapter = adapter

        this.logout.setOnClickListener {
            logoutUser()
        }
    }

    private fun example(): List<Data> {
        return listOf(
            Data("Title 1", "Creator 1"),
            Data("Title 2", "Creator 2"),
            Data("Title 3", "Creator 3"),
            Data("Title 4", "Creator 4"),
            Data("Title 5", "Creator 5"),
            Data("Title 6", "Creator 6"),
            Data("Title 7", "Creator 7"),
            Data("Title 8", "Creator 8"),
            Data("Title 9", "Creator 9"),
            Data("Title 10", "Creator 10")
        )
    }


    private fun logoutUser() {
        val apiService = ApiClient.retrofit.create(ApiService::class.java)
        val call = apiService.logout()

        call.enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@AdminActivity,
                        "Logged Out Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@AdminActivity, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@AdminActivity,
                        "Error: ${response.errorBody()?.string()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Toast.makeText(
                    this@AdminActivity,
                    "Network error occurred: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


}