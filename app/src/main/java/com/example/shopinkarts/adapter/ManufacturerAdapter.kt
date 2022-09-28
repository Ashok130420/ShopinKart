package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsManufacturerBinding
import com.example.shopinkarts.response.PreferredManufacturer

class ManufacturerAdapter(val context: Context, val arrayList: ArrayList<PreferredManufacturer>) :
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
        val itemDetails = arrayList[position]
        holder.itemsManufacturerBinding.apply {
            nameTV.text = itemDetails.brandName
            Glide.with(context).load(itemDetails.brandImage).into(imageIV)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsManufacturerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val itemsManufacturerBinding = itemView

    }
}