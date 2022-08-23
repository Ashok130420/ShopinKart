package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsParticularItemBinding

class ParticularItemAdapter(val context: Context) :
    RecyclerView.Adapter<ParticularItemAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsParticularItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_particular_item,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 21
    }

    inner class ViewHolder(itemView: ItemsParticularItemBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsParticularItemBinding = itemView

    }
}