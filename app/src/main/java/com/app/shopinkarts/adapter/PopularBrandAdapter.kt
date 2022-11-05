package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemPopularBrandBinding
import com.app.shopinkarts.response.PreferredManufacturer

class PopularBrandAdapter(val context: Context, val arrayList: ArrayList<PreferredManufacturer>) :
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
        val itemDetails = arrayList[position]
        holder.binding.apply {
            nameTV.text = itemDetails.brandName
            Glide.with(context).load(itemDetails.brandImage).into(imageIV)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemPopularBrandBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemPopularBrandBinding = itemView
    }
}