package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.SubCategoriesActivity
import com.example.shopinkarts.databinding.ItemsCategoriesBinding
import com.example.shopinkarts.model.CategoriesModel

class CategoriesAdapter(val context: Context) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsCategoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_categories,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // val item = arrayList[position]

        /*holder.binding.apply {
            categoriesNameTv.text = arrayList[position].title

            if (item.isChecked) {
                categoriesView.visibility = View.VISIBLE
                categoriesNameTv.setTextColor(Color.parseColor("#3669C9"))
            } else {
                categoriesView.visibility = View.INVISIBLE
                categoriesNameTv.setTextColor(Color.parseColor("#9EA6BE"))
            }
        }*/
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubCategoriesActivity::class.java)
            context.startActivity(intent)
/*
            if (item.isChecked) {
                item.isChecked = false

            } else {
                arrayList.forEach { element -> element.isChecked = false }
                item.isChecked = true
            }
            notifyDataSetChanged()*/
        }
    }

    override fun getItemCount(): Int {
        return 10
    }

    inner class ViewHolder(itemView: ItemsCategoriesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsCategoriesBinding = itemView

    }
}