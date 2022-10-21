package com.example.shopinkarts.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.YourCartAdapter.Companion.updateQty
import com.example.shopinkarts.databinding.ItemsCartColorSizeQtyBinding
import com.example.shopinkarts.response.Variants

class CartColorSizeAdapter(val context: Context, val arrayList: ArrayList<Variants>) :
    RecyclerView.Adapter<CartColorSizeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsCartColorSizeQtyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.items_cart_color_size_qty,
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemDetails = arrayList[position]
        holder.binding.apply {

            quantityShowTV.text = itemDetails.quantity.toString()
            sizeBlockTV.text = itemDetails.size
            colorIV.setBackgroundColor(Color.parseColor(itemDetails.color))

  /*          plusQuantityTV.setOnClickListener {

                updateQty = itemDetails.quantity + 1

                Log.d("defaultCQ", itemDetails.quantity.toString())
                Log.d("after+CQ", updateQty.toString())
                Log.d("stock", itemDetails.stock.toString())
                Log.d(" itemDetails.quantity", itemDetails.quantity.toString())

                unitPrice = itemDetails.discountedPrice.toInt()

                if (updateQty <= itemDetails.stock) {

                    itemDetails.quantity = updateQty

                    Log.d("stockstockstock", unitPrice.toString())
                    Log.d("stockstockstock", updateQty.toString())
                    Log.d("stockstockstock", "${unitPrice * updateQty}")

                    updatePrice = unitPrice * updateQty
                    itemDetails.totalAmount = updatePrice

                    notifyDataSetChanged()
                    sharedPreference.setArray()

                } else {
                    Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show()
                }
                ProductCartActivity.getInstance().updatedCal()
            }

            minusQuantityTV.setOnClickListener {

                Log.d("defaultCQ", itemDetails.quantity.toString())
                Log.d("after+CQ", (itemDetails.quantity.toInt() - 1).toString())
                Log.d("stock", itemDetails.stock.toString())

                updateQty = itemDetails.quantity - 1

                val unitPrice = itemDetails.discountedPrice.toInt()

                if (updateQty == 0) {
                    //delete item
                    arrayList.removeAt(position)
                    selectedVIDs.removeAt(position)
                    sharedPreference.setArray()


                    notifyDataSetChanged()


                } else {

                    //update item
                    itemDetails.quantity = updateQty
                    updatePrice = unitPrice * updateQty
                    itemDetails.totalAmount = updatePrice
                    sharedPreference.setArray()
                    notifyDataSetChanged()

                }
                ProductCartActivity.getInstance().updatedCal()
            }*/

        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemsCartColorSizeQtyBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemsCartColorSizeQtyBinding = itemView
    }
}