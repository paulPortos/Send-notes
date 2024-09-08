package com.group1.notamonotako.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.MyNotesAdapter
import com.group1.notamonotako.api.requests_responses.NotesData
import com.group1.notamonotako.views.SettingsActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MyNotes : Fragment() {
    lateinit var btnSettings : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
   val view = inflater.inflate(R.layout.fragment_my_notes, container, false)



        val recyclerView: RecyclerView = view.findViewById(R.id.rv_mynotes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnSettings = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }

    val adapter = MyNotesAdapter(example())
    recyclerView.adapter = adapter
    return view
    }


    private fun example(): List<NotesData> {
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
        for (i in 0..10) {
            dataList.add(
                NotesData(title[i % title.size], contents[i % contents.size])
            )
        }
        return dataList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}

