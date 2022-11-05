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
import com.app.shopinkarts.databinding.ItemsTopWearBinding
import com.app.shopinkarts.response.SubCategory

class TopWearAdapter(val context: Context, val arrayList: ArrayList<SubCategory>) :
    RecyclerView.Adapter<TopWearAdapter.ViewHolder>() {


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

        val itemDetails = arrayList[position]
        holder.itemsTopWearBinding.apply {
            topWearTV.text = itemDetails.subCategoryName
            Glide.with(context).load(itemDetails.imageURL).into(topWearIV)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ParticularItemActivity::class.java)
            intent.putExtra("subCategoryName", itemDetails.subCategoryName)
            intent.putExtra("particularItemId", itemDetails._id)
            intent.putExtra("imageURL", itemDetails.imageURL)
            intent.putExtra("type", 1)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsTopWearBinding) : RecyclerView.ViewHolder(itemView.root) {
        val itemsTopWearBinding = itemView
    }

}