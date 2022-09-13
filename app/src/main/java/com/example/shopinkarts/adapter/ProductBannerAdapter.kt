package com.example.shopinkarts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.model.ProductBannerSlide


class ProductBannerAdapter(var context: Context, val introSlides: List<String>) :
    RecyclerView.Adapter<ProductBannerAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {

        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_slide_item_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
       Glide.with(context).load(introSlides[position]).into(holder.imageIcon)
    }

    class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

         val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)



    }

}