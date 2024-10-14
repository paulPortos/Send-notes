package com.group1.notamonotako.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.AccountManager
import com.group1.notamonotako.api.requests_responses.comments.getComments


class CommentsAdapter (val context: Context, private var data: List<getComments>) : RecyclerView.Adapter<CommentsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btnDelete: ImageView = view.findViewById(R.id.btnDelete)
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
        holder.message.text = item.comment

        val currentUsername = AccountManager.getUsername() // Ensure this returns the logged-in user's username

        holder.itemView.setOnLongClickListener {
            // Check if the username of the comment matches the current user's username
            if (item.username == currentUsername) {
                // Allow long press and toggle checkbox visibility
                holder.btnDelete.visibility = if (holder.btnDelete.visibility == View.VISIBLE) {
                    holder.btnDelete.visibility = View.GONE
                    holder.btnDelete.isClickable = true
                    View.GONE
                } else {
                    holder.btnDelete.visibility = View.VISIBLE
                    View.VISIBLE
                }
                true // Indicate the long press was handled
            } else {
                // Show a message or take another action if the user doesn't match
                Toast.makeText(holder.itemView.context, "You can only modify your own notes.", Toast.LENGTH_SHORT).show()
                false // Indicate the long press was not handled
            }
        }
    }





    override fun getItemCount(): Int {
        return data.size
    }
}