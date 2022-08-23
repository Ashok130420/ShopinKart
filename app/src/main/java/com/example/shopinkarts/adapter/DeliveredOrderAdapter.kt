package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.TrackOrderActivity
import com.example.shopinkarts.databinding.ItemsDeliveredOrderBinding

class DeliveredOrderAdapter(val context: Context) :
    RecyclerView.Adapter<DeliveredOrderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsDeliveredOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_delivered_order,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productRV.adapter = holder.productAdapter
        holder.binding.productRV.isNestedScrollingEnabled = false
        holder.binding.trackOrderStatusTV.setOnClickListener {
            val intent = Intent(context, TrackOrderActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return 3
    }

    inner class ViewHolder(itemView: ItemsDeliveredOrderBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsDeliveredOrderBinding = itemView

        var productAdapter: ProductAdapter = ProductAdapter(context)

    }
}