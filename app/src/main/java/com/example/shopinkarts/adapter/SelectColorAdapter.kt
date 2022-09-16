package com.example.shopinkarts.adapter


import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ProductDetailsActivity
import com.example.shopinkarts.databinding.ItemsSelectColorBinding
import com.example.shopinkarts.model.SelectColorModel

class SelectColorAdapter(
    val context: Context,
    val arrayList: ArrayList<SelectColorModel>
) :
    RecyclerView.Adapter<SelectColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsSelectColorBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.items_select_color,
                parent, false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = arrayList[position]
        holder.binding.apply {

            backGroundColorCL.setBackgroundColor(Color.parseColor(item.colors))

            if (item.isChecked) {
                rightIconIV.visibility = View.VISIBLE
                backGroundCL.setBackgroundResource(R.drawable.button_blue_radius5)

            } else {
                rightIconIV.visibility = View.INVISIBLE
                //backGroundCL.setBackgroundResource(R.drawable.button_white_radius5)
                backGroundCL.setBackgroundColor(Color.TRANSPARENT)

            }
        }


        holder.itemView.setOnClickListener {
            if (item.isChecked) {

                ProductDetailsActivity.colorSize = 0
                Log.d("colorSize0", ProductDetailsActivity.colorSize.toString())
                ProductDetailsActivity.getInstance().colorSizeUpdate()
                ProductDetailsActivity.getInstance().inActiveAddCart()
                item.isChecked = false
            } else {
                arrayList.forEach { element -> element.isChecked = false }
                ProductDetailsActivity.selectedColor = arrayList[position].colors
                ProductDetailsActivity.colorSize = 1
                Log.d("colorSize1", ProductDetailsActivity.colorSize.toString())
                ProductDetailsActivity.getInstance().colorSizeUpdate()
                ProductDetailsActivity.getInstance().activeAddCart()
                ProductDetailsActivity.getInstance().updateLastNumber()

                item.isChecked = true
            }

            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsSelectColorBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsSelectColorBinding = itemView

    }

}
