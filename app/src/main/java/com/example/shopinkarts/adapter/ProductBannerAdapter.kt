package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ImageZoomActivity
import com.example.shopinkarts.databinding.ProductSlideItemContainerBinding
import com.example.shopinkarts.databinding.SlideItemContainerBinding


class ProductBannerAdapter(var context: Context, val introSlides: List<String>) :
    RecyclerView.Adapter<ProductBannerAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {

        val binding: ProductSlideItemContainerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.product_slide_item_container,
            parent,
            false
        )

        return IntroSlideViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {

        holder.binding.apply {
            Glide.with(context).load(introSlides[position]).into(imageSlideIcon)

            imageSlideIcon.setOnClickListener {
                val intent = Intent(context, ImageZoomActivity::class.java)
                intent.putExtra("imageUrl", introSlides[position])
                context.startActivity(intent)

            }
        }

    }

    inner class IntroSlideViewHolder(itemView: ProductSlideItemContainerBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: ProductSlideItemContainerBinding = itemView

    }
}