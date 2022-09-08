package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsNewlyAddedBinding
import com.example.shopinkarts.response.NewlyAdded

class NewlyAddedAdapter(val context: Context, val arrayList: ArrayList<NewlyAdded>) :
    RecyclerView.Adapter<NewlyAddedAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsNewlyAddedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_newly_added,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {

            Glide.with(context).load(itemDetails.productImages[0]).into(newlyAddedIV)
            productNameTV.text = itemDetails.productName
            priceTV.text = "Rs ${itemDetails.price}"

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("productId",itemDetails._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsNewlyAddedBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsNewlyAddedBinding = itemView

    }
}