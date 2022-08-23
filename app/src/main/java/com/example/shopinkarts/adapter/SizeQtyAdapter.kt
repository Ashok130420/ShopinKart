package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsSizeQtyBinding

class SizeQtyAdapter(val context: Context) :
    RecyclerView.Adapter<SizeQtyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsSizeQtyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_size_qty,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(itemView: ItemsSizeQtyBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSizeQtyBinding = itemView
    }
}