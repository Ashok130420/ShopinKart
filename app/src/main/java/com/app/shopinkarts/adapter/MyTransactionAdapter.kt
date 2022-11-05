package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemsMyTransactionBinding

class MyTransactionAdapter(val context: Context) :
    RecyclerView.Adapter<MyTransactionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsMyTransactionBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_my_transaction,
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

    inner class ViewHolder(itemView: ItemsMyTransactionBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: ItemsMyTransactionBinding = itemView
    }

}