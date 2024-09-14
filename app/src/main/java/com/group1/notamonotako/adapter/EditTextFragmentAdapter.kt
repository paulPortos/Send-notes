package com.group1.notamonotako.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.group1.notamonotako.fragments.EditTextFragment

class EditTextFragmentAdapter(fragment: Fragment, private val dataList: MutableList<Pair<String, String>>)
    : FragmentStateAdapter(fragment) {

    private val fragmentMap = mutableMapOf<Int, EditTextFragment>()

    override fun getItemCount(): Int = dataList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = EditTextFragment()
        fragment.arguments = Bundle().apply {
            putString("title", dataList[position].first)
            putString("contents", dataList[position].second)
        }
        fragmentMap[position] = fragment
        return fragment
    }

    fun getFragment(position: Int): EditTextFragment? {
        return fragmentMap[position]
    }
}
