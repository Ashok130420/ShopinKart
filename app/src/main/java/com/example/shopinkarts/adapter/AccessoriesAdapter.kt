package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsAccessoriesBinding

class AccessoriesAdapter(val context: Context) :
    RecyclerView.Adapter<AccessoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsAccessoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_accessories,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(itemView: ItemsAccessoriesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsAccessoriesBinding = itemView
    }
}