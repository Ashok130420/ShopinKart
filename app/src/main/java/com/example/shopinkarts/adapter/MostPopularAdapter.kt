package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsMostPopularBinding

class MostPopularAdapter(val context: Context) :
    RecyclerView.Adapter<MostPopularAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsMostPopularBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_most_popular,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 14
    }

    inner class ViewHolder(itemView: ItemsMostPopularBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsMostPopularBinding = itemView
    }
}