package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ItemsSimilarProductsBinding

class SimilarProductsAdapter(val context: Context) :
    RecyclerView.Adapter<SimilarProductsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSimilarProductsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_similar_products,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 20
    }

    inner class ViewHolder(itemView: ItemsSimilarProductsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSimilarProductsBinding = itemView

    }
}