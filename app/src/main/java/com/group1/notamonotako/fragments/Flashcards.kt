package com.group1.notamonotako.fragments

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.group1.notamonotako.R
import com.group1.notamonotako.adapter.EditTextFragmentAdapter

class  Flashcards : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btn_right: ImageButton
    private lateinit var btn_back: ImageButton
    private val dataList = mutableListOf<Pair<String, String>>()
    private lateinit var adapter: EditTextFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_flashcards, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        btn_right = view.findViewById(R.id.btn_right)
        btn_back = view.findViewById(R.id.btn_back)

        dataList.add(Pair("Title", "Content"))


        adapter = EditTextFragmentAdapter(this, dataList)
        viewPager.adapter = adapter

        btn_right.setOnClickListener {
            saveCurrentPageData()
            val currentItem = viewPager.currentItem
            // Add a new page if the current one is the last page
            if (currentItem == adapter.itemCount - 1) {
                dataList.add(Pair("", "")) // Add a new blank page
                adapter.notifyDataSetChanged()
            }
            // Move to the next page
            viewPager.currentItem = currentItem + 1
        }

        btn_back.setOnClickListener {
            val home = Home()
            val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, home)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }

    private fun saveCurrentPageData() {
        val currentItem = viewPager.currentItem
        val fragment = adapter.getFragment(currentItem)
        if (fragment != null) {
            dataList[currentItem] = Pair(fragment.getTitle(), fragment.getContents())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
