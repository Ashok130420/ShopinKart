package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.DashBoardActivity
import com.example.shopinkarts.activity.SubCategoriesActivity
import com.example.shopinkarts.databinding.ItemsShopForBinding
import com.example.shopinkarts.response.ShopFor

class ShopForAdapter(val context: Context, val arrayList: ArrayList<ShopFor>) :
    RecyclerView.Adapter<ShopForAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsShopForBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_shop_for,
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemDetails = arrayList[position]
        holder.itemsShopForBinding.apply {
            nameTV.text = itemDetails.categoryName
            Glide.with(context).load(itemDetails.categoryIcon).into(iconIV)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DashBoardActivity::class.java)
            itemDetails._id
            intent.putExtra("from", "categories")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsShopForBinding) : RecyclerView.ViewHolder(itemView.root) {
        var itemsShopForBinding = itemView

    }
}