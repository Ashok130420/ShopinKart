package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.ProductDetailsActivity
import com.app.shopinkarts.databinding.ItemsSelectSizeBinding
import com.app.shopinkarts.model.SelectSizeModel

class SelectSizeAdapter(val context: Context, val arrayList: ArrayList<SelectSizeModel>) :
    RecyclerView.Adapter<SelectSizeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSelectSizeBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_select_size,
                parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = arrayList[position]

        holder.binding.apply {

            sizeTV.text = arrayList[position].sizes
            if (item.isChecked) {
                backGroundColorCL.setBackgroundColor(context.resources.getColor(R.color.primary_Blue))
                sizeTV.setTextColor(context.resources.getColor(R.color.white))
            } else {
                backGroundColorCL.setBackgroundColor(context.resources.getColor(R.color.light_Gray))
                sizeTV.setTextColor(context.resources.getColor(R.color.primary_text))
            }
        }

        holder.itemView.setOnClickListener {
            if (item.isChecked) {
                ProductDetailsActivity.sizeOfSize = 0
                ProductDetailsActivity.getInstance().sizeUpdate()
                ProductDetailsActivity.getInstance().inActiveAddCart()
                item.isChecked = false
            } else {
                arrayList.forEach { element -> element.isChecked = false }
                ProductDetailsActivity.selectedSize = arrayList[position].sizes
                ProductDetailsActivity.sizeOfSize = 1
                ProductDetailsActivity.getInstance().sizeUpdate()
                ProductDetailsActivity.getInstance().activeAddCart()
                ProductDetailsActivity.getInstance().updateCurrentNumber()
                item.isChecked = true
            }
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsSelectSizeBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSelectSizeBinding = itemView
    }
}