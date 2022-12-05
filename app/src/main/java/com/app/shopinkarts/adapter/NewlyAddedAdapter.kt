package com.app.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.ProductDetailsActivity
import com.app.shopinkarts.databinding.ItemsNewlyAddedBinding
import com.app.shopinkarts.fragments.HomeFragment
import com.app.shopinkarts.response.NewlyAdded

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {
            if (itemDetails.productImages.isNotEmpty()) {
                Glide.with(context).load(itemDetails.productImages[0]).into(newlyAddedIV)
            }
          
            productNameTV.text = itemDetails.productName
//            priceTV.text = "Rs ${itemDetails.price}"
            ratingTV.text = itemDetails.avgRating.toString()

            var discount = 0
            if (itemDetails.discountType == 1) {
                discount = (itemDetails.price * itemDetails.discount) / 100
                priceTV.text = "Rs ${(itemDetails.price)-(discount)}"

            } else if (itemDetails.discountType == 0) {
                discount = (itemDetails.price - itemDetails.discount)
                priceTV.text = "Rs $discount"

            }

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("productId", itemDetails._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {

        return if (HomeFragment.listItems == 0) {
            if (arrayList.size <= 3) {
                arrayList.size
            } else {
                3
            }
        } else {
            arrayList.size
        }
    }

    inner class ViewHolder(itemView: ItemsNewlyAddedBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsNewlyAddedBinding = itemView

    }
}