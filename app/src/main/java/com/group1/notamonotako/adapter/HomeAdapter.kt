package com.group1.notamonotako.adapter

import ApiService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.AccountManager
import com.group1.notamonotako.api.requests_responses.public_notes.getPublicNotes
import com.group1.notamonotako.api.requests_responses.signin.User
import com.group1.notamonotako.views.ViewHome
import kotlinx.coroutines.launch

class HomeAdapter(val context: Context, private var data: List<getPublicNotes>,private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val contents: TextView = view.findViewById(R.id.contents)
        val commentCount : TextView = view.findViewById(R.id.commentcount)
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

        // Fetch comment count for the specific note
        val noteId = item.notesId ?: -1
        fetchCommentCount(noteId, holder.commentCount)


        // Get the current user's ID


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



    }


    // Method to fetch the comment count for a specific note
    private fun fetchCommentCount(noteId: Int, commentCountView: TextView) {
        lifecycleOwner.lifecycleScope.launch {
            try{

                val token = TokenManager.getToken()
                val apiService = RetrofitInstance.create(ApiService::class.java)
                val response = apiService.getCommentsByNoteId("Bearer $token",noteId)

                if (response.isSuccessful) {
                    val commentCount = response.body()?.comment_count ?: 0
                    commentCountView.text = "Comments: $commentCount"
                } else {
                    commentCountView.text = "Comments: 0"
                }


        }catch (e: Exception){
                Log.e("HomeAdapter", "Error fetching comment count", e)

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
