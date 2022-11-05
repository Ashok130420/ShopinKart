package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsReviewsBinding

class ReviewsAdapter(val context: Context) : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsReviewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_reviews,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 14
    }

    inner class ViewHolder(itemView: ItemsReviewsBinding) : RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsReviewsBinding = itemView
    }
}
