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

class Home : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnSettings = view.findViewById<ImageButton>(R.id.btnSettings)
        btnSettings.setOnClickListener {
            val intent = Intent(requireContext(),SettingsActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}