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
import com.group1.notamonotako.R
import com.group1.notamonotako.views.SettingsActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.views.HomeAdapter
import com.group1.notamonotako.views.HomeData

class Home : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rvhome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = HomeAdapter(example())
        recyclerView.adapter = adapter
        return view
    }

    private fun example(): List<HomeData> {
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
        val dataList = mutableListOf<HomeData>()
        for (i in 0..10) {
            dataList.add(
                HomeData(title[i % title.size], contents[i % contents.size])
            )
        }
        return dataList
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}