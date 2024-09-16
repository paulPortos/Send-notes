package com.group1.notamonotako.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.GetFlashcards

class MyFlashcardsAdapter(private var context: Context, private val flashcardsList: List<GetFlashcards>) : RecyclerView.Adapter<MyFlashcardsAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val cards: TextView = view.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_myflashcards_grid, parent, false)
        return ItemViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = flashcardsList[position]
        holder.title.text = item.title

        // Join the cards list into a single string, separating by commas or any other delimiter
        holder.cards.text = item.cards.joinToString(", ")
    }

    override fun getItemCount(): Int = flashcardsList.size
}
