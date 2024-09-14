package com.group1.notamonotako.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.group1.notamonotako.R

class EditTextFragment : Fragment() {

    private lateinit var titleEditText: EditText
    private lateinit var contentsEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_text, container, false)

        titleEditText = view.findViewById(R.id.title)
        contentsEditText = view.findViewById(R.id.contents)

        val args = arguments
        if (args != null) {
            titleEditText.setText(args.getString("title", ""))
            contentsEditText.setText(args.getString("contents", ""))
        }

        return view
    }

    fun getTitle(): String = titleEditText.text.toString()
    fun getContents(): String = contentsEditText.text.toString()
}
