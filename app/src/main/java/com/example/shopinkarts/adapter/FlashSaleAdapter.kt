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
import com.example.shopinkarts.databinding.ItemsFlashSaleBinding
import com.example.shopinkarts.response.FlashSale

class FlashSaleAdapter(val context: Context, val arrayList: ArrayList<FlashSale>) :
    RecyclerView.Adapter<FlashSaleAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsFlashSaleBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_flash_sale,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.itemsFlashSaleBinding.apply {
            Glide.with(context).load(itemDetails.productImages[0]).into(discountIV)
            productNameTV.text = itemDetails.productName
            priceTV.text = "Rs ${itemDetails.price}"
            discountTV.text = "${itemDetails.discount} %OFF"

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsFlashSaleBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val itemsFlashSaleBinding = itemView
    }
}