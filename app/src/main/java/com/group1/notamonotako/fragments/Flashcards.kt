package com.group1.notamonotako.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.FlashcardsData
import com.group1.notamonotako.adapter.FlashcardsAdapter

class Flashcards : Fragment() {
    private lateinit var btn_back :ImageButton
    private lateinit var layoutManager: LinearLayoutManager



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_flashcards, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rvflashcards)
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL,false)
        recyclerView.layoutManager = layoutManager

        val adapter = FlashcardsAdapter(example())
        recyclerView.adapter = adapter



        btn_back = view.findViewById(R.id.btn_back)


        btn_back.setOnClickListener{
            val Home =Home()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, Home)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        return view

    }

    private fun example(): List<FlashcardsData> {
        val title = listOf(
            "Title 1",
            "Title 2",
            "Title 3"
        )
        val contents = listOf(
            "One",
            "Two",
            "Three"
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