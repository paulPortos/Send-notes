package com.group1.notamonotako.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.GetFlashcards

class FlashcardsAdapter(private var data: List<GetFlashcards>)  : RecyclerView.Adapter<FlashcardsAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: EditText = view.findViewById(R.id.title)
        val contents: EditText = view.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_fragment_horizontal_row, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]

        // Set title
        holder.title.setText(item.title)

        // Set contents by joining the list of cards into a single string (e.g., separated by commas or newlines)
        holder.contents.setText(item.cards.joinToString(separator = ", "))
    }

    override fun getItemCount(): Int = data.size
}
