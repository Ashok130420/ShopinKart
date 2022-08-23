package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.OrderDetailsActivity
import com.example.shopinkarts.activity.TrackOrderActivity
import com.example.shopinkarts.databinding.ItemsDeliveredProductBinding

class ProductAdapter(val context: Context) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsDeliveredProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_delivered_product,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.sizeQtyRV.adapter = holder.sizeQtyAdapter
        holder.binding.sizeQtyRV.isNestedScrollingEnabled = false
        holder.itemView.setOnClickListener {
            val intent = Intent(context, OrderDetailsActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: ItemsDeliveredProductBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsDeliveredProductBinding = itemView
        var sizeQtyAdapter: SizeQtyAdapter = SizeQtyAdapter(context)
    }
}