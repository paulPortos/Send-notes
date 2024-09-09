package com.group1.notamonotako.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.HomeData

class HomeAdapter(private var data: List<HomeData>) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val contents: TextView = view.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_home_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.title
        holder.contents.text = item.contents
    }

    override fun getItemCount(): Int = data.size
}
