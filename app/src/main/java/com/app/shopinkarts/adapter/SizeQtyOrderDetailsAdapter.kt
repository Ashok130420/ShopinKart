package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsSizeQtyOrderDetailsBinding

class SizeQtyOrderDetailsAdapter(val context: Context) :
    RecyclerView.Adapter<SizeQtyOrderDetailsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsSizeQtyOrderDetailsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_size_qty_order_details,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class ViewHolder(itemView: ItemsSizeQtyOrderDetailsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSizeQtyOrderDetailsBinding = itemView
    }
}