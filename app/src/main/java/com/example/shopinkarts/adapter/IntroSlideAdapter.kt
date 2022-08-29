package com.example.shopinkarts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.SlideItemContainerBinding
import com.example.shopinkarts.model.IntroSlide


class IntroSlideAdapter(private val introSlides: List<IntroSlide>) :
    RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {

        val binding: SlideItemContainerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.slide_item_container,
            parent,
            false
        )

        return IntroSlideViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }

    class IntroSlideViewHolder(itemView: SlideItemContainerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: SlideItemContainerBinding = itemView

        fun bind(introSlide: IntroSlide) {
            binding.imageSlideIcon.setImageResource(introSlide.icon)
        }
    }
}