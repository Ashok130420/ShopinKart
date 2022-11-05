package com.app.shopinkarts.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsSizeQtyBinding
import com.app.shopinkarts.response.Variants

class SizeQtyAdapter(val context: Context, val arrayList: List<Variants>) :
    RecyclerView.Adapter<SizeQtyAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemsSizeQtyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_size_qty,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {

            colorIV.setBackgroundColor(Color.parseColor(itemDetails.color))
            sizeBlockTV.text = itemDetails.size
            qtyValueTV.text = itemDetails.quantity.toString()

        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsSizeQtyBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSizeQtyBinding = itemView
    }
}