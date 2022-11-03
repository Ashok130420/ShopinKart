package com.example.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.DashBoardActivity
import com.example.shopinkarts.activity.ParticularItemActivity
import com.example.shopinkarts.activity.SubCategoriesActivity
import com.example.shopinkarts.databinding.CategoryBannerContainerBinding
import com.example.shopinkarts.model.Banner2Slide
import com.example.shopinkarts.model.Banner3Slide
import com.example.shopinkarts.model.CategoryBannerSlide
import com.example.shopinkarts.response.Banner


class CategoryBannerAdapter(val context: Context, val arrayList: ArrayList<Banner>) :
    RecyclerView.Adapter<CategoryBannerAdapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        val binding: CategoryBannerContainerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.category_banner_container,
            parent,
            false
        )

        return IntroSlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        val itemDetails = arrayList[position]

        holder.binding.apply {
            Glide.with(context).load(itemDetails.bannerImage).into(imageSlideCategoryBannerIV)
        }

//      banner type 0 for external
        when (itemDetails.bannerType) {
            0 -> {
                holder.itemView.setOnClickListener {
                    Log.d("BannerBanner", itemDetails.bannerURL)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(itemDetails.bannerURL)
                    )
                    val chooseIntent = Intent.createChooser(intent, "Choose from below")
                    context.startActivity(chooseIntent)
                }

                //      banner type 0 for categories
            }
            1 -> {
                holder.itemView.setOnClickListener {
                    Log.d("BannerBanner", itemDetails.bannerURL)
                    val intent = Intent(context, DashBoardActivity::class.java)
                    intent.putExtra("from", "categories")
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startActivity(intent)
                }

                //      banner type 0 for sub_categories
            }
            2 -> {
                holder.itemView.setOnClickListener {
                    Log.d("BannerBanner", itemDetails.bannerURL)
                    val intent = Intent(context, SubCategoriesActivity::class.java)
                    intent.putExtra("categoryId", itemDetails.bannerURL)
                    context.startActivity(intent)
                }

                //      banner type 0 for product
            }
            3 -> {
                holder.itemView.setOnClickListener {
                    Log.d("BannerBanner", itemDetails.bannerURL)
                    val intent = Intent(context, ParticularItemActivity::class.java)
                    intent.putExtra("particularItemId", itemDetails.bannerURL)
                    context.startActivity(intent)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class IntroSlideViewHolder(itemView: CategoryBannerContainerBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        val binding: CategoryBannerContainerBinding = itemView


    }

}