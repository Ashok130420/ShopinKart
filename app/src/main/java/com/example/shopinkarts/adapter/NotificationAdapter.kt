package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsNotificationsBinding
import com.example.shopinkarts.response.Notification

class NotificationAdapter(val context: Context, val arrayList: ArrayList<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsNotificationsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_notifications, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {
            titleTV.text = itemDetails.title
            descriptionTV.text = itemDetails.message
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsNotificationsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsNotificationsBinding = itemView
    }
}