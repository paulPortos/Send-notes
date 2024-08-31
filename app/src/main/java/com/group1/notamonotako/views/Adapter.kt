package com.group1.notamonotako.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R

class Adapter(
    private var data: List<Data>  // Use 'data' to match the variable used in other parts of the code
) : RecyclerView.Adapter<Adapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val creator: TextView = view.findViewById(R.id.creator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]  // Renamed from 'notes' to 'item' to avoid confusion
        holder.title.text = item.title
        holder.creator.text = item.creator
    }

    override fun getItemCount(): Int = data.size
}
