package com.group1.notamonotako.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.comments.Comments

class CommentsAdapter(private val context: Context, private val comments: MutableList<Comments>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val username: TextView = view.findViewById(R.id.username)
        val message: TextView = view.findViewById(R.id.message)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.rv_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.username.text = comment.username
        holder.message.text = comment.message
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    fun addComment(comment: Comments) {
        comments.add(comment)
        notifyItemInserted(comments.size - 1)
    }


}
