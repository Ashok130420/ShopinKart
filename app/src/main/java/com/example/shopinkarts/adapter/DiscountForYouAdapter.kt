package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsDiscountForYouBinding

class DiscountForYouAdapter(val context: Context) :
    RecyclerView.Adapter<DiscountForYouAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsDiscountForYouBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_discount_for_you,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(itemView: ItemsDiscountForYouBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsDiscountForYouBinding = itemView

    }
}