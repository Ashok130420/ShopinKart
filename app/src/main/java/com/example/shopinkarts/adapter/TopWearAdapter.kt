package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ParticularItemActivity
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsTopWearBinding

class TopWearAdapter(val context: Context) : RecyclerView.Adapter<TopWearAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsTopWearBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_top_wear,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ParticularItemActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 17
    }

    inner class ViewHolder(itemView: ItemsTopWearBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsTopWearBinding = itemView
    }

}