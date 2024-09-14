package com.group1.notamonotako.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.group1.notamonotako.R
import com.group1.notamonotako.views.SettingsActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.group1.notamonotako.adapter.HomeAdapter
import com.group1.notamonotako.api.requests_responses.home.HomeData

class Home : Fragment() {
    lateinit var flashcardsFabBtn : FloatingActionButton
    lateinit var mainFabBtn : FloatingActionButton
    lateinit var notesFabBtn :FloatingActionButton
    lateinit var btnSettings :ImageButton
    lateinit var notesTV :TextView
    lateinit var flashcardsTV :TextView

    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)
    }

    private var areFabButtonsVisible = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        notesTV =view.findViewById(R.id.notesTV)
        flashcardsTV =view.findViewById(R.id.flashcardsTV)
        notesFabBtn = view.findViewById(R.id.notesFabBtn)
        mainFabBtn = view.findViewById(R.id.mainFabBtn)
        flashcardsFabBtn = view.findViewById(R.id.flashcardsFabBtn)
        btnSettings = view.findViewById(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.rvhome)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        flashcardsFabBtn.setOnClickListener {
            val Flashcards = Flashcards()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, Flashcards)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        notesFabBtn.setOnClickListener {
            val Notes =Notes()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, Notes)
            transaction.addToBackStack(null)
            transaction.commit()
        }


        mainFabBtn.setOnClickListener {

            if (areFabButtonsVisible) {
                shrinkFab()
            } else {
                expandFab()
            }

        }

        val adapter = HomeAdapter(example())
        recyclerView.adapter = adapter
        return view
    }

    private fun shrinkFab() {

        mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        notesFabBtn.startAnimation(toBottomFabAnim)
        flashcardsFabBtn.startAnimation(toBottomFabAnim)
        notesTV.startAnimation(toBottomFabAnim)
        flashcardsTV.startAnimation(toBottomFabAnim)
        areFabButtonsVisible = !areFabButtonsVisible
    }

    private fun expandFab() {
       mainFabBtn.startAnimation(rotateClockWiseFabAnim)
       notesFabBtn.startAnimation(fromBottomFabAnim)
        flashcardsFabBtn.startAnimation(fromBottomFabAnim)
        notesTV.startAnimation(fromBottomFabAnim)
        flashcardsTV.startAnimation(fromBottomFabAnim)
        areFabButtonsVisible = !areFabButtonsVisible
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
        for (i in title.indices) {
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