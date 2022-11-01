package com.example.shopinkarts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.DashBoardActivity.Companion.selectedVIDs
import com.example.shopinkarts.activity.ProductCartActivity
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ItemsCartColorSizeQtyBinding
import com.example.shopinkarts.response.Variants

var updateQty = 0
var updatePrice = 0
var unitPrice = 0

lateinit var sharedPreference: SharedPreference

class CartColorSizeAdapter(val context: Context, val arrayList: ArrayList<Variants>) :
    RecyclerView.Adapter<CartColorSizeAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemsCartColorSizeQtyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.items_cart_color_size_qty, parent, false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        sharedPreference = SharedPreference(context)

        val itemDetails = arrayList[position]
        holder.binding.apply {

            quantityShowTV.text = itemDetails.quantity.toString()
            sizeBlockTV.text = itemDetails.size
            colorIV.setBackgroundColor(Color.parseColor(itemDetails.color))


            plusQuantityTV.setOnClickListener {

                ProductCartActivity.arrayTotal.clear()

//                updateQty = itemDetails.quantity
//                unitPrice = itemDetails.price / itemDetails.quantity
                updateQty = itemDetails.quantity + 1

                Log.d("defaultCQ", itemDetails.quantity.toString())
                Log.d("after+CQ", updateQty.toString())
                Log.d("stock", itemDetails.stock.toString())
                Log.d(" itemDetails.quantity", itemDetails.quantity.toString())


                if (updateQty <= itemDetails.stock) {

                    itemDetails.quantity = updateQty

//                    Log.d("plus_UnitPrice", unitPrice.toString())
//                    Log.d("plus_UpdateQty", updateQty.toString())
//                    Log.d("plus_update_price", "${unitPrice * updateQty}")
//                    Log.d("plus_price", itemDetails.price.toString())

//                    updatePrice = unitPrice * updateQty

                    notifyDataSetChanged()

                    ProductCartActivity.cartInstance.update()
                    sharedPreference.setArray()

                } else {
                    Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show()
                }

            }

            minusQuantityTV.setOnClickListener {

                ProductCartActivity.arrayTotal.clear()


//                 updateQty = itemDetails.quantity
//                 unitPrice = itemDetails.price / itemDetails.quantity
                updateQty = itemDetails.quantity - 1


                if (updateQty == 0) {
                    //delete item
                    arrayList.removeAt(position)
                    selectedVIDs.removeAt(position)
                    sharedPreference.setArray()

                    notifyDataSetChanged()
                    ProductCartActivity.cartInstance.update()

                } else  {

                    //update item
//                    val uPrice = itemDetails.price / itemDetails.quantity
                    itemDetails.quantity = updateQty
//                    updatePrice = uPrice * updateQty
//                    itemDetails.price = updatePrice
//
//                    Log.d("minus_UnitPrice", unitPrice.toString())
//                    Log.d("minus_UpdateQty", updateQty.toString())
//                    Log.d("minus_update_price", "${unitPrice * updateQty}")
//                    Log.d("minus_price", itemDetails.price.toString())

                    sharedPreference.setArray()
                    notifyDataSetChanged()
                    ProductCartActivity.cartInstance.update()

                }
//                 ProductCartActivity.cartInstance.update()
                ProductCartActivity.getInstance().updatedCal()
            }

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