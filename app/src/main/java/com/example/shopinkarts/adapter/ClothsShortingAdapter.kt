package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsClothsShortingBinding
import com.example.shopinkarts.model.ClothShortingModel

class ClothsShortingAdapter(val context: Context, val arrayList: ArrayList<ClothShortingModel>) :
    RecyclerView.Adapter<ClothsShortingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsClothsShortingBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_cloths_shorting,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = arrayList[position]

        holder.binding.apply {
            itemsNameTv.text = arrayList[position].cloths
            if (item.isChecked) {
                clothsView.visibility = View.VISIBLE
                itemsNameTv.setTextColor(context.resources.getColor(R.color.white))
            } else {
                clothsView.visibility = View.GONE
                itemsNameTv.setTextColor(context.resources.getColor(R.color.text_light_green))

            }

        }

        holder.itemView.setOnClickListener {
            if (item.isChecked) {
                item.isChecked = false
            } else {
                arrayList.forEach { element -> element.isChecked = false }
                item.isChecked = true
            }
            notifyDataSetChanged()

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsClothsShortingBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsClothsShortingBinding = itemView

    }

}