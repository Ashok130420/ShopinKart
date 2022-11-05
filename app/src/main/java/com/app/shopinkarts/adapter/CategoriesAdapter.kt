package com.app.shopinkarts.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.SubCategoriesActivity
import com.app.shopinkarts.databinding.ItemsCategoriesBinding
import com.app.shopinkarts.response.Category

class CategoriesAdapter(val context: Context, val arrayList: ArrayList<Category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsCategoriesBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_categories,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]

        holder.itemView.setOnClickListener {
            val intent = Intent(context, SubCategoriesActivity::class.java)
            intent.putExtra("categoryId", itemDetails._id)
            intent.putExtra("categoryName", itemDetails.categoryName)
            intent.putExtra("categoryIcon", itemDetails.categoryIcon)
            context.startActivity(intent)

        }
        holder.itemsCategoriesBinding.apply {

            nameTV.text = itemDetails.categoryName
            descriptionTV.text = itemDetails.categorySubName
            Glide.with(context).load(itemDetails.categoryIcon).into(itemsIV)
//            if (arrayList[position].creationTimeStamp != null)
//                nameTV.text = Utils.formatDateFromDateString(
//                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
//                    "dd/MMM/yyyy",
//                    arrayList[position].creationTimeStamp
//                )
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsCategoriesBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var itemsCategoriesBinding = itemView
    }
}