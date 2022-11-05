package com.app.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.ParticularItemActivity
import com.app.shopinkarts.databinding.ItemsManufacturerBinding
import com.app.shopinkarts.response.PreferredManufacturer

class ManufacturerAdapter(val context: Context, val arrayList: ArrayList<PreferredManufacturer>) :
    RecyclerView.Adapter<ManufacturerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsManufacturerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_manufacturer, parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.itemsManufacturerBinding.apply {
            nameTV.text = itemDetails.brandName
            Glide.with(context).load(itemDetails.brandImage).into(imageIV)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ParticularItemActivity::class.java)
            intent.putExtra("manufacturerName", itemDetails.brandName)
            intent.putExtra("manufacturerItemId", itemDetails._id)
            intent.putExtra("manufacturerImageURL", itemDetails.brandImage)
            intent.putExtra("type", 0)
            context.startActivity(intent)
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