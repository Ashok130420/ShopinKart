package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsOrderDetailsBinding

class OrderDetailsAdapter(val context: Context) :
    RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsOrderDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_order_details,
            parent, false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.sizeQtyOrderDetailsRV.adapter = holder.sizeQtyOrderDetailsAdapter
        holder.binding.sizeQtyOrderDetailsRV.isNestedScrollingEnabled = false

    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(itemView: ItemsOrderDetailsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsOrderDetailsBinding = itemView
        var sizeQtyOrderDetailsAdapter: SizeQtyOrderDetailsAdapter =
            SizeQtyOrderDetailsAdapter(context)
    }
}