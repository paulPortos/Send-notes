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
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.FlashcardsAdapter
import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.api.requests_responses.notes.PostnotesRequest
import com.group1.notamonotako.api.requests_responses.signin.Login
import com.group1.notamonotako.views.Mynotes
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class   Notes : Fragment() {

    private lateinit var leftButton : ImageButton
    private lateinit var doneButton : ImageButton
    private lateinit var addtitle: EditText
    private lateinit var addcontent: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)


        leftButton = view.findViewById(R.id.leftButton)
        doneButton =view.findViewById(R.id.doneButton)
        addtitle = view.findViewById(R.id.title)
        addcontent = view.findViewById(R.id.content)


        leftButton.setOnClickListener{
            val Home =Home()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, Home)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        doneButton.setOnClickListener{
            val title = addtitle.text.toString()
            val contents = addcontent.text.toString()
            CreateData(title,contents)
        }

        return view

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    private fun CreateData(title: String, contents: String) {
        lifecycleScope.launch {
            val apiService = RetrofitInstance.create(ApiService::class.java)
            val postNotes = PostnotesRequest(title = title, contents = contents, public = false, to_public = false)

            try {
                // Make the network call and get the response
                val response = apiService.createNote(postNotes)

                if (response.isSuccessful) {
                    // Navigate to the Mynotes activity only once
                    val intent = Intent(requireContext(), Mynotes::class.java)
                    intent.putExtra("title", title)
                    intent.putExtra("contents", contents)

                    // Set the flags to clear the back stack
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    // Finish the current activity or fragment to avoid going back
                    activity?.finish()
                } else {
                    Toast.makeText(requireContext(), "Failed to create note", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Toast.makeText(requireContext(), "HTTP error: ${e.message}", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.d("NotesFragment", e.message.toString())
            }
        }
    }
}