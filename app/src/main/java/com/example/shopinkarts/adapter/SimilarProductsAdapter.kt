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
import com.example.shopinkarts.databinding.ItemsSimilarProductsBinding
import com.example.shopinkarts.response.NewlyAdded

class SimilarProductsAdapter(val context: Context, val arrayList: ArrayList<NewlyAdded>) :
    RecyclerView.Adapter<SimilarProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSimilarProductsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_similar_products,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {

            Glide.with(context).load(itemDetails.productImages[0]).into(imageIV)
            productNameTV.text = itemDetails.productName
            priceTV.text = "Rs ${itemDetails.price}"

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", itemDetails._id)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsSimilarProductsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSimilarProductsBinding = itemView

    }
}