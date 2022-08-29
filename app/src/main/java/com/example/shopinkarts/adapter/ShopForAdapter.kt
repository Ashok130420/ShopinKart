package com.example.shopinkarts.adapter

import android.content.Context
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsShopForBinding
import com.example.shopinkarts.response.ShopFor
import com.squareup.picasso.Picasso
import java.security.AccessController.getContext

class ShopForAdapter(val context: Context, val arrayList: ArrayList<ShopFor>) :
    RecyclerView.Adapter<ShopForAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsShopForBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_shop_for,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemDetails = arrayList[position]
        holder.itemsShopForBinding.apply {
            nameTV.text = itemDetails.categoryName
            // iconIV.setImageResource(itemDetails.categoryIcon)
            Picasso.get().load(itemDetails.categoryIcon).fit().into(iconIV)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsShopForBinding) : RecyclerView.ViewHolder(itemView.root) {
        var itemsShopForBinding = itemView

    }
}