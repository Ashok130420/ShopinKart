package com.example.shopinkarts.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.DashBoardActivity.Companion.selectedVIDs
import com.example.shopinkarts.databinding.ItemYourCartBinding
import com.example.shopinkarts.model.CartModel

class YourCartAdapter(val context: Context, var arrayList: ArrayList<CartModel>) :
    RecyclerView.Adapter<YourCartAdapter.ViewHolder>() {

    var updateQty = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemYourCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_your_cart,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val itemDetails = arrayList[position]
        holder.binding.apply {

            productNameTV.text = itemDetails.itemName
            discountedPriceTV.text = "Rs ${(itemDetails.totalAmount)}.00"
            updateQty = itemDetails.quantity
            quantityShowTV.text = updateQty.toString()
            sizeBlockTV.text = itemDetails.size
            colorIV.setBackgroundColor(Color.parseColor(itemDetails.color))
            Log.d("COLOR", itemDetails.color)
//          totalAmountTV.text = "Total Amount-Rs ${itemDetails.totalAmount}"
            Glide.with(context).load(itemDetails.imageUrl).into(imageIV)

            plusQuantityTV.setOnClickListener {

                updateQty = itemDetails.quantity + 1

                Log.d("defaultCQ", itemDetails.quantity.toString())
                Log.d("after+CQ", updateQty.toString())
                Log.d("stock", itemDetails.stock.toString())
                Log.d(" itemDetails.quantity", itemDetails.quantity.toString())

                val unitPrice = itemDetails.discountedPrice.toInt()

                if (updateQty <= itemDetails.stock) {

                    itemDetails.quantity = updateQty
                    itemDetails.discountedPrice = (unitPrice * updateQty).toString()

                    Log.d("stockstockstock", unitPrice.toString())
                    Log.d("stockstockstock", updateQty.toString())
                    Log.d("stockstockstock", itemDetails.discountedPrice)

                    notifyDataSetChanged()

                } else {
                    Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show()
                }
            }

            minusQuantityTV.setOnClickListener {
                Log.d("defaultCQ", itemDetails.quantity.toString())
                Log.d("after+CQ", (itemDetails.quantity.toInt() - 1).toString())
                Log.d("stock", itemDetails.stock.toString())

                updateQty = itemDetails.quantity.toInt() - 1

                val unitPrice = itemDetails.discountedPrice.toInt()

                if (updateQty == 0) {
                    //delete item
                    arrayList.removeAt(position)

                } else {
                    //update item
                    itemDetails.quantity = updateQty
                    itemDetails.discountedPrice = (unitPrice * updateQty).toString()
                }
                notifyDataSetChanged()


            }

            deleteIconIV.setOnClickListener {
                arrayList.removeAt(position)
                notifyDataSetChanged()

                //deleting item from selected vId

                for (element in selectedVIDs) {
                    if (element == itemDetails.vId) {
                        selectedVIDs.removeAt(position)
                    }
                }

                if (arrayList.isEmpty()) {
                    (context as Activity).finish()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemYourCartBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemYourCartBinding = itemView

    }

}