package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsShopForBinding

class ShopForAdapter(val context: Context) : RecyclerView.Adapter<ShopForAdapter.ViewHolder>() {


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

    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(itemView: ItemsShopForBinding) : RecyclerView.ViewHolder(itemView.root) {

    }
}