package com.group1.notamonotako.fragments

import ApiService
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.MyNotesAdapter
import com.group1.notamonotako.api.requests_responses.NotesData
import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.views.SettingsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val apiService = RetrofitInstance.create(ApiService::class.java)

        apiService.getNotes().enqueue(object : Callback<List<Note>> {
            override fun onResponse(call: Call<List<Note>>, response: Response<List<Note>>) {
                if (response.isSuccessful) {
                    val notes = response.body()!!

                    if (isAdded) {
                        myNotesAdapter = MyNotesAdapter(requireContext(), notes)
                        rv_mynotes.adapter = myNotesAdapter
                    }
                } else {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Failed to fetch notes", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<Note>>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                    Log.d("MyNotesFragment", t.message.toString())
                }
            }
        })
    }


   /* private fun example(): List<NotesData> {
        val title = listOf(
            "Title 1",
            "Title 2",
            "Title 3"
        )
        val contents = listOf(
            "Lorem Ipsum dolor",
            "Lorem Ipsum set amet",
            "Lorem Ipsum"
        )
        val dataList = mutableListOf<NotesData>()
        for (i in title.indices) {
            dataList.add(
                NotesData(title[i % title.size], contents[i % contents.size])
            )
        }
        return dataList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }*/

}

