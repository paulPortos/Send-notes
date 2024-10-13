package com.group1.notamonotako.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.public_notes.getPublicNotes
import com.group1.notamonotako.views.ViewHome

class HomeAdapter(val context: Context, private var data: List<getPublicNotes>) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.note_checkbox)
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

        // Get the current user's ID
        val currentUserId = TokenManager.getToken()?.toIntOrNull() // Assuming this returns the user ID
        val isTokenValid = TokenManager.isTokenValid() // Check if the token is valid

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ViewHome::class.java)
            val updatedAtDate = item.updated_at?.substringBefore("T") ?: "No Date"
            intent.putExtra("note_id", item.notesId ?: -1)
            intent.putExtra("title", item.title ?: "No Title")
            intent.putExtra("contents", item.contents ?: "No Contents")
            intent.putExtra("public", item.public ?: false)
            intent.putExtra("updated_at", updatedAtDate)
            it.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            // Only allow long press if the current user ID matches the note's user ID
            if (isTokenValid && currentUserId != null && item.user_id == currentUserId) {
                // Toggle checkbox visibility
                if (holder.checkbox.visibility == View.VISIBLE) {
                    holder.checkbox.visibility = View.GONE
                    holder.checkbox.isChecked = false
                } else {
                    holder.checkbox.visibility = View.VISIBLE
                    holder.checkbox.isChecked = true
                }
                true // Return true to indicate the long press was handled
            } else {
                // Log if the user is not allowed to long press
                if (!isTokenValid) {
                    Log.d("HomeAdapter", "Token is not valid. Cannot perform action.")
                } else {
                    Log.d("HomeAdapter", "User does not have permission to modify this note.")
                }
                false // Return false to indicate the long press was not handled
            }
        }
    }

    fun setFilteredList(data: List<getPublicNotes>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
