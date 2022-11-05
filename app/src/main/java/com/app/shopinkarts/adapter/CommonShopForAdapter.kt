package com.app.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopinkarts.R
import com.app.shopinkarts.databinding.ItemPopularBrandBinding
import com.app.shopinkarts.databinding.ItemsFlashSaleBinding
import com.app.shopinkarts.databinding.ItemsNewlyAddedBinding
import com.app.shopinkarts.databinding.ItemsShopForBinding

class CommonShopForAdapter(val context: Context, val viewType: Int) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val shop = 1
    val newAdded = 2
    val sale = 3

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  RecyclerView.ViewHolder {
        if (viewType == 1) {
//            val view: View = LayoutInflater.from(context).inflate(R.layout.items_shop_for, parent, false)
            val itemsShopForBinding: ItemsShopForBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.items_shop_for,
                parent,
                false
            )
            return ShopViewHolder(itemsShopForBinding)
        } else if (viewType == 2) {
            val itemsNewlyAddedBinding: ItemsNewlyAddedBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.items_newly_added,
                parent,
                false
            )
            return Shop2ViewHolder(itemsNewlyAddedBinding)
        } else if(viewType == 3){
            val itemPopularBrandBinding: ItemPopularBrandBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.item_popular_brand,
                parent,
                false
            )
            return Shop3ViewHolder(itemPopularBrandBinding)

        }else {
            val itemsFlashSaleBinding: ItemsFlashSaleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.items_flash_sale,
                parent,
                false
            )
            return Shop4ViewHolder(itemsFlashSaleBinding)

        }

//        return ViewHolder(binding)
    }
    override fun getItemViewType(position: Int): Int {
        return if (viewType == 1) {
           shop
        } else if (viewType == 2) {
            newAdded
        }else{
            sale
        }
    }

    inner class ShopViewHolder(itemView: ItemsShopForBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    inner class Shop2ViewHolder(itemView: ItemsNewlyAddedBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    inner class Shop3ViewHolder(itemView: ItemPopularBrandBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }
    inner class Shop4ViewHolder(itemView: ItemsFlashSaleBinding) :
        RecyclerView.ViewHolder(itemView.root) {

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == ShopViewHolder::class.java) {
            val viewHolder = holder as ShopViewHolder
        } else if (holder.javaClass == Shop2ViewHolder::class.java) {
            val viewHolder = holder as Shop2ViewHolder
        }else if(holder.javaClass == Shop3ViewHolder::class.java) {
            val viewHolder = holder as Shop3ViewHolder
        }
    }

    override fun getItemCount(): Int {
        return 5
    }
/*
    inner class ViewHolder(itemView: ItemsShopForBinding) : RecyclerView.ViewHolder(itemView.root) {

    }*/
}