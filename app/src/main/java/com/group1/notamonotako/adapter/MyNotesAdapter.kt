package com.group1.notamonotako.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.notes.Note
import com.group1.notamonotako.views.Mynotes

class MyNotesAdapter(val context: Context, val notelist: List<Note>) : RecyclerView.Adapter<MyNotesAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(Noteview : View) : RecyclerView.ViewHolder(Noteview) {
        val title: TextView = Noteview.findViewById(R.id.title)
        val Notes : ConstraintLayout = Noteview.findViewById(R.id.layout_notes)
        val contents: TextView = Noteview.findViewById(R.id.contents)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val Noteviewer: View = LayoutInflater.from(context)
            .inflate(R.layout.rv_mynotes_row, parent, false)
        return NotesViewHolder(Noteviewer)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val item = notelist[position]
        holder.title.text = item.title
        holder.contents.text = item.contents


            holder.Notes.setOnClickListener {
            val intent = Intent(it.context, Mynotes::class.java)

            intent.putExtra("title", item.title)
            intent.putExtra("contents", item.contents)
            intent.putExtra("date", item.updated_at)
            intent.putExtra("note_id",item.id)

            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

}
