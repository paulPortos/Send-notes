package com.group1.notamonotako.fragments

import ApiService
import android.content.Intent
import android.os.Bundle
import android.graphics.Rect
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.MyFlashcardsAdapter
import com.group1.notamonotako.views.GradientText
import com.group1.notamonotako.views.SettingsActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class MyFlashcards : Fragment() {
    private lateinit var btnSettings: ImageButton
    private lateinit var progressBar : ProgressBar
    private lateinit var myFlashcardsAdapter: MyFlashcardsAdapter
    private lateinit var rv_myFlashcards: RecyclerView
    private lateinit var tvMyFlashcards: TextView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_flashcards, container, false)
        btnSettings = view.findViewById(R.id.btnSettings)
        rv_myFlashcards = view.findViewById(R.id.rv_myflashcards)
        progressBar = view.findViewById(R.id.progressBar)
        tvMyFlashcards = view.findViewById(R.id.tvMyFlashcards)
        progressBar.visibility = View.INVISIBLE


        // Set up Grid layout with 2 columns
        rv_myFlashcards.layoutManager = GridLayoutManager(requireContext(), 2)
        GradientText.setGradientText(tvMyFlashcards,requireContext())

        // Set up spacing between flashcards (16dp space between items)
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.spacing) // Replace with a value in dimens.xml
        rv_myFlashcards.addItemDecoration(SpacesItemDecoration(spacingInPixels))

        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
            progressBar.visibility = View.VISIBLE

        }

        fetchFlashcards()

        return view
    }

    private fun fetchFlashcards() {
        lifecycleScope.launch {
            try {
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = withContext(Dispatchers.IO) {
                    apiService.getFlashcards() // Using execute() for synchronous call
                }

                if (response.isSuccessful) {
                    val flashcards = response.body()
                    if (isAdded && flashcards != null) {
                        myFlashcardsAdapter = MyFlashcardsAdapter(requireContext(), flashcards)
                        rv_myFlashcards.adapter = myFlashcardsAdapter
                    } else {
                        if (isAdded) {
                            Toast.makeText(requireContext(), "No flashcards available", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.INVISIBLE

                        }
                    }
                } else {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Failed to fetch flashcards", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.INVISIBLE

                    }
                }
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE

            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("flashcards", e.message.toString())
                progressBar.visibility = View.INVISIBLE

            }
        }
    }

    // Inner class to handle spacing between items in the grid
    inner class SpacesItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            val position = parent.getChildAdapterPosition(view)

            outRect.left = space / 2
            outRect.right = space / 2
            outRect.bottom = space


        }
    }
}
