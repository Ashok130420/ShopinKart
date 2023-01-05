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
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.databinding.ItemsTopRatedBinding
import com.app.shopinkarts.response.TopRated

class TopRatedAdapter(val context: Context, val arrayList: ArrayList<TopRated>) :
    RecyclerView.Adapter<TopRatedAdapter.ViewHolder>() {

    lateinit var sharedPreference: SharedPreference
    var userType = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsTopRatedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_top_rated,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        sharedPreference = SharedPreference(context)
        userType = sharedPreference.getUserType().toString()

        val itemDetails = arrayList[position]
        holder.binding.apply {
            if (itemDetails.productImages.isNotEmpty()) {
                Glide.with(context).load(itemDetails.productImages[0]).into(imageIV)
            }

            if (userType == "0") {
                priceTV.text = "Rs ${itemDetails.price}"
            }else if(userType == "1") {
                priceTV.text = "Rs ${itemDetails.priceReselling}"
            }
            productNameTV.text = itemDetails.productName
            ratingTV.text=itemDetails.avgRating.toString()

            var discount = 0
            if (userType == "0") {
                if (itemDetails.discountType == 1) {
                    discount = (itemDetails.price * itemDetails.discount) / 100
                    priceTV.text = "Rs ${(itemDetails.price) - (discount)}"
                } else if (itemDetails.discountType == 0) {
                    discount = (itemDetails.price - itemDetails.discount)
                    priceTV.text = "Rs $discount"
                }

            } else if (userType == "1") {
                if (itemDetails.discountType == 1) {
                    discount = (itemDetails.priceReselling * itemDetails.discount) / 100
                    priceTV.text = "Rs ${(itemDetails.priceReselling) - (discount)}"
                } else if (itemDetails.discountType == 0) {
                    discount = (itemDetails.priceReselling - itemDetails.discount)
                    priceTV.text = "Rs $discount"
                }
            }
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

    inner class ViewHolder(itemView: ItemsTopRatedBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsTopRatedBinding = itemView
    }
}