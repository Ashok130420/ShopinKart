package com.app.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.ProductDetailsActivity
import com.app.shopinkarts.databinding.ItemsSearchBinding
import com.app.shopinkarts.response.Product
import kotlin.collections.ArrayList

class SearchAdapter(val context: Context, var arrayList: ArrayList<Product>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSearchBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_search, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails=arrayList[position]

        holder.binding.apply {
            Glide.with(context).load(itemDetails.productImages[0]).into(imageIV)
            productNameTV.text = itemDetails.productName
            priceTV.text = "Rs ${itemDetails.price}"
            ratingTV.text=itemDetails.avgRating.toString()
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

    inner class ViewHolder(itemView: ItemsSearchBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSearchBinding = itemView
    }

}