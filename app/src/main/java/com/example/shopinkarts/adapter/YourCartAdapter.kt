package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemYourCartBinding

class YourCartAdapter(val context: Context) :
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
        holder.binding.apply {

        }

    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(itemView: ItemYourCartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemYourCartBinding = itemView

    }
}