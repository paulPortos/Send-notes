package com.group1.notamonotako.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.group1.notamonotako.R
import com.group1.notamonotako.api.requests_responses.notification.GetNotification

class NotificationAdapter(val context: Context, private var data: List<GetNotification>) : RecyclerView.Adapter<NotificationAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val notificationType: TextView = view.findViewById(R.id.notification_type)
        val email: TextView = view.findViewById(R.id.email)
        val message: TextView = view.findViewById(R.id.message)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflatedView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_notification, parent, false)
        return ItemViewHolder(inflatedView)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = data[position]
        holder.notificationType.text = item.notificationType
        holder.email.text = item.email
        holder.message.text = item.message
    }
    override fun getItemCount(): Int {
        return data.size
    }
}