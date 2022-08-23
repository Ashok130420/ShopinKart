package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemPopularBrandBinding

class PopularBrandAdapter(val context: Context) :
    RecyclerView.Adapter<PopularBrandAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemPopularBrandBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_popular_brand,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(itemView: ItemPopularBrandBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }
}