package com.example.shopinkarts.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.model.Banner2Slide
import com.example.shopinkarts.model.Banner3Slide
import com.example.shopinkarts.model.CategoryBannerSlide


class CategoryBannerAdapter(private val arrayList: List<CategoryBannerSlide>) :
    RecyclerView.Adapter<CategoryBannerAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {

        return IntroSlideViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_banner_container, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(arrayList[position])
    }

    class IntroSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideCategoryBannerIV)

        fun bind(introSlide: CategoryBannerSlide) {
            imageIcon.setImageResource(introSlide.icon)
        }

    }

}