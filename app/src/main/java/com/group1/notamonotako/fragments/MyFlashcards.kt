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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.FlashcardsData
import com.group1.notamonotako.views.GridSpacingMyFlashcards
import com.group1.notamonotako.adapter.MyFlashcardsAdapter
import com.group1.notamonotako.views.SettingsActivity


class MyFlashcards : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_flashcards, container, false)


        val btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_myflashcards)
        val spanCount = 2
        val spacing = 25 // Space in pixels
        val includeEdge = true
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.addItemDecoration(GridSpacingMyFlashcards(spanCount, spacing, includeEdge))

        val adapter = MyFlashcardsAdapter(example())
        recyclerView.adapter = adapter
        return view
    }

    private fun example(): List<FlashcardsData> {
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
        val dataList = mutableListOf<FlashcardsData>()
        for (i in title.indices) {
            dataList.add(
                FlashcardsData(title[i % title.size], contents[i % contents.size])
            )
        }
        return dataList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}