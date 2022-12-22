package com.app.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.ProductDetailsActivity
import com.app.shopinkarts.databinding.ItemsParticularItemBinding
import com.app.shopinkarts.response.Product

class ParticularItemAdapter(val context: Context, var arrayList: ArrayList<Product>) :
    RecyclerView.Adapter<ParticularItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsParticularItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_particular_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.itemsParticularItemBinding.apply {
            Glide.with(context).load(itemDetails.productImages[0]).into(imageIV)
            tShirtNameTV.text = itemDetails.productName
            priceTV.text = "Rs ${itemDetails.price}"
            ratingTV.text=itemDetails.avgRating.toString()

            if (itemDetails.discountType == 1) {
                discountTV.text = "${itemDetails.discount} %OFF"
            } else if (itemDetails.discountType == 0) {
                discountTV.text = "Rs ${itemDetails.discount} OFF"
            } else {
                discountTV.visibility = View.GONE
                discountTagIV.visibility = View.GONE
            }
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

    inner class ViewHolder(itemView: ItemsParticularItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val itemsParticularItemBinding = itemView

    }
}