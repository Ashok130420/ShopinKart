package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsManufacturerBinding

class ManufacturerAdapter(val context: Context) :
    RecyclerView.Adapter<ManufacturerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsManufacturerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_manufacturer,
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

    inner class ViewHolder(itemView: ItemsManufacturerBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }
}