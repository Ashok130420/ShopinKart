package com.example.shopinkarts.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsSelectColorBinding
import com.example.shopinkarts.model.SelectColorModel

class SelectColorAdapter(val context: Context, val arrayList: ArrayList<SelectColorModel>) :
    RecyclerView.Adapter<SelectColorAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSelectColorBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_select_color,
                parent, false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = arrayList[position]
        holder.binding.apply {
            backGroundColorCL.setBackgroundColor(Color.parseColor(arrayList[position].colors))
            if (item.isChecked) {
                rightIconIV.visibility = View.VISIBLE
                backGroundCL.setBackgroundResource(R.drawable.button_blue_radius5)
            } else {
                rightIconIV.visibility = View.INVISIBLE
                //backGroundCL.setBackgroundResource(R.drawable.button_white_radius5)
                backGroundCL.setBackgroundColor(Color.TRANSPARENT)
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

    inner class ViewHolder(itemView: ItemsSelectColorBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSelectColorBinding = itemView
    }
}
