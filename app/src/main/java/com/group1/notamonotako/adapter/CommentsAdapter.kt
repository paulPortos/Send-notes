package com.group1.notamonotako.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.comments.Comments

class CommentsAdapter (val context: Context, private var data: List<Comments>) : RecyclerView.Adapter<CommentsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val message: TextView = view.findViewById(R.id.message)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_comment, parent, false)
        return ItemViewHolder(inflatedView)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.username.text = item.username
        holder.message.text = item.message
    }
    override fun getItemCount(): Int {
        return data.size
    }
}