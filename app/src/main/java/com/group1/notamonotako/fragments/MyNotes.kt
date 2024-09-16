package com.group1.notamonotako.fragments

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.MyNotesAdapter
import com.group1.notamonotako.views.SettingsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MyNotes : Fragment() {
    lateinit var btnSettings : ImageButton
    lateinit var myNotesAdapter: MyNotesAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rv_mynotes: RecyclerView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_notes, container, false)



        rv_mynotes = view.findViewById(R.id.rv_mynotes)
        rv_mynotes.layoutManager = LinearLayoutManager(requireContext())

        btnSettings = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
        rv_mynotes.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        fetchNotes()
    return view
    }

    private fun fetchNotes() {
        lifecycleScope.launch {
            try {
                // Network call should happen on IO thread
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.getNotes().execute() // Using execute() for synchronous call
                }

                if (response.isSuccessful) {
                    val notes = response.body()

                    // Ensure fragment is still attached before updating UI
                    if (isAdded && notes != null) {
                        myNotesAdapter = MyNotesAdapter(requireContext(), notes)
                        rv_mynotes.adapter = myNotesAdapter
                    } else {
                        if (isAdded) {
                            Toast.makeText(requireContext(), "No notes available", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Failed to fetch notes", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("addnotes", e.message.toString())
            }
            }
        }
    }



