package com.example.shopinkarts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.model.ProductBannerSlide


class ProductBannerAdapter(private val introSlides: List<ProductBannerSlide>) :
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
        holder.bind(introSlides[position])
    }

    class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)

        fun bind(introSlide: ProductBannerSlide) {
            imageIcon.setImageResource(introSlide.icon)
        }

    }

}