package com.example.shopinkarts.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemYourCartBinding
import com.example.shopinkarts.model.CartModel

class YourCartAdapter(val context: Context, var arrayList: ArrayList<CartModel>) :
    RecyclerView.Adapter<YourCartAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemYourCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_your_cart,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {
            productNameTV.text = itemDetails.itemName
            discountedPriceTV.text = itemDetails.discountedPrice
            quantityShowTV.text = itemDetails.quantity
            sizeBlockTV.text = itemDetails.size
            colorIV.setBackgroundColor(Color.parseColor(itemDetails.color))
            Log.d("COLOR", itemDetails.color)
//            totalAmountTV.text = "Total Amount-Rs ${itemDetails.totalAmount}"
            Glide.with(context).load(itemDetails.imageUrl).into(imageIV)

        }
    }

    override fun getItemCount(): Int {
        return arrayList.count()
    }

    inner class ViewHolder(itemView: ItemYourCartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemYourCartBinding = itemView

    }
}