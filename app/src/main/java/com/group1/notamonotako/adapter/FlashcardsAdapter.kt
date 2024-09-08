package com.group1.notamonotako.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.FlashcardsData

class FlashcardsAdapter(private var data: List<FlashcardsData>)  : RecyclerView.Adapter<FlashcardsAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: EditText  = view.findViewById(R.id.title)
        val contents: EditText = view.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_fragment_horizontal_row, parent, false)
        return ItemViewHolder(inflatedView)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.title.setText(item.title)
        holder.contents.setText(item.contents)
    }

    override fun getItemCount(): Int = data.size
}

