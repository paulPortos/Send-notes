package com.group1.notamonotako.fragments

import ApiService
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
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.group1.notamonotako.R
import com.group1.notamonotako.views.SettingsActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.group1.notamonotako.adapter.HomeAdapter
import com.group1.notamonotako.api.requests_responses.public_notes.getPublicNotes
import com.group1.notamonotako.views.AddFlashcards
import com.group1.notamonotako.views.AddNotes
import com.group1.notamonotako.views.GradientText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.Locale

class Home : Fragment() {
    lateinit var flashcardsFabBtn : FloatingActionButton
    lateinit var mainFabBtn : FloatingActionButton
    lateinit var notesFabBtn :FloatingActionButton
    lateinit var btnSettings :ImageButton
    lateinit var notesTV :TextView
    lateinit var swiperefresh : SwipeRefreshLayout
    lateinit var flashcardsTV :TextView
    private lateinit var progressBar : ProgressBar
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var rvhome: RecyclerView
    private lateinit var tvSendNotes : TextView
    private lateinit var viewBlur : View
    private lateinit var svSearchView :SearchView
    private var data: List<getPublicNotes> = listOf()


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
    private var hasShownNoDataToast = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        notesTV =view.findViewById(R.id.notesTV)
        flashcardsTV =view.findViewById(R.id.flashcardsTV)
        notesFabBtn = view.findViewById(R.id.notesFabBtn)
        mainFabBtn = view.findViewById(R.id.mainFabBtn)
        flashcardsFabBtn = view.findViewById(R.id.flashcardsFabBtn)
        btnSettings = view.findViewById(R.id.btnSettings)
        progressBar = view.findViewById(R.id.progressBar)
        progressBar.visibility = View.INVISIBLE
        rvhome = view.findViewById(R.id.rvhome)
        tvSendNotes = view.findViewById(R.id.tvSendNotes)
        rvhome.layoutManager = LinearLayoutManager(requireContext())
        viewBlur = view.findViewById(R.id.viewBlur)
        viewBlur.visibility = View.GONE
        svSearchView = view.findViewById(R.id.svSearchView)
        swiperefresh = view.findViewById(R.id.swipeRefreshHome)



        svSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true

            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }


        })



        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)


        }



        flashcardsFabBtn.setOnClickListener {
            val intent = Intent(requireContext(), AddFlashcards::class.java) // Create intent for Notes activity
            startActivity(intent)
            progressBar.visibility = View.INVISIBLE

        }
        swiperefresh.setOnRefreshListener {
            fetchPublicNotes()
            swiperefresh.isRefreshing = false
        }

        notesFabBtn.setOnClickListener {
            val intent = Intent(requireContext(), AddNotes::class.java) // Create intent for Notes activity
            startActivity(intent)
            progressBar.visibility = View.INVISIBLE
        }

        mainFabBtn.setOnClickListener {
            progressBar.visibility = View.INVISIBLE
            if (areFabButtonsVisible) {
                shrinkFab()
            } else {
                expandFab()
            }
        }
        GradientText.setGradientText(tvSendNotes, requireContext())
        rvhome.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this.context)
        fetchPublicNotes()
        return view
    }

    private fun fetchPublicNotes(){
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.getPublicNotes()
                }
                if (response.isSuccessful){
                    val publicNotes = response.body()
                    if (isAdded && publicNotes != null){
                        data = publicNotes // Store in data to put in datalist
                        val adapter = HomeAdapter(requireContext(), publicNotes)
                        rvhome.adapter = adapter
                    }
                }
            } catch (e: HttpException){
                Toast.makeText(requireContext(), "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException){
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun shrinkFab() {
        mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        notesFabBtn.startAnimation(toBottomFabAnim)
        flashcardsFabBtn.startAnimation(toBottomFabAnim)
        notesTV.startAnimation(toBottomFabAnim)
        flashcardsTV.startAnimation(toBottomFabAnim)
        areFabButtonsVisible = !areFabButtonsVisible
        viewBlur.visibility = View.GONE
    }

    private fun expandFab() {
       mainFabBtn.startAnimation(rotateClockWiseFabAnim)
       notesFabBtn.startAnimation(fromBottomFabAnim)
        flashcardsFabBtn.startAnimation(fromBottomFabAnim)
        notesTV.startAnimation(fromBottomFabAnim)
        flashcardsTV.startAnimation(fromBottomFabAnim)
        areFabButtonsVisible = !areFabButtonsVisible
        viewBlur.visibility = View.VISIBLE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    private fun filterList(query: String?) {
        if (query != null && query.isNotEmpty()) {
            val filteredList = data.filter { it.title.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) }

            if (filteredList.isEmpty() && !hasShownNoDataToast) {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()
                hasShownNoDataToast = true
                (rvhome.adapter as HomeAdapter).setFilteredList(emptyList())
            } else if (filteredList.isNotEmpty()) {
                hasShownNoDataToast = false
                (rvhome.adapter as HomeAdapter).setFilteredList(filteredList)
            }
        } else {
            (rvhome.adapter as HomeAdapter).setFilteredList(data)
            hasShownNoDataToast = false
        }
    }

}