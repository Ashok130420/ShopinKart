package com.app.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.DashBoardActivity
import com.app.shopinkarts.activity.ParticularItemActivity
import com.app.shopinkarts.activity.SubCategoriesActivity
import com.app.shopinkarts.databinding.Banner3ContainerBinding
import com.app.shopinkarts.response.Banner


class Banner3Adapter(val context: Context, val arrayList: ArrayList<Banner>) :
    RecyclerView.Adapter<Banner3Adapter.IntroSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {

        val binding: Banner3ContainerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.banner3_container, parent, false
        )
        return IntroSlideViewHolder(binding)

    }


    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {
            Glide.with(context).load(itemDetails.bannerImage).into(imageSlide3IV)
        }

//      banner type 0 for external
        when (itemDetails.bannerType) {
            0 -> {
                holder.itemView.setOnClickListener {
                    Log.d("BannerBanner", itemDetails.bannerURL)
                    val intent = Intent(
                        Intent.ACTION_VIEW, Uri.parse(itemDetails.bannerURL)
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


    class IntroSlideViewHolder(itemView: Banner3ContainerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: Banner3ContainerBinding = itemView

    }

}