package com.example.shopinkarts.adapter

import android.annotation.SuppressLint
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
import com.example.shopinkarts.activity.DashBoardActivity
import com.example.shopinkarts.activity.DashBoardActivity.Companion.selectedVIDs
import com.example.shopinkarts.activity.ProductCartActivity
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ItemYourCartBinding
import com.example.shopinkarts.model.CartModel

class YourCartAdapter(val context: Context, var arrayList: ArrayList<CartModel>) :
    RecyclerView.Adapter<YourCartAdapter.ViewHolder>() {


    var unitPrice = 0
    var updatePrice = 0
    lateinit var sharedPreference: SharedPreference
    lateinit var cartColorSizeAdapter: CartColorSizeAdapter


    var finalTotalPrice = 0
    var totalPrice = 0
    var finalQuantity = 0
    var totalQuantity = 0


    companion object {
//        var cartInstance: YourCartAdapter = YourCartAdapter(context)
//
//        fun getInstance(): YourCartAdapter {
//            return cartInstance
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemYourCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_your_cart, parent, false
        )

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        sharedPreference = SharedPreference(context)

        val itemDetails = arrayList[position]
        holder.binding.apply {

            productNameTV.text = itemDetails.itemName

            updatePrice = itemDetails.totalAmount

//            discountedPriceTV.text = "Rs ${updatePrice}.00"
//            actualPriceTV.text =
//                "Rs ${ DashBoardActivity.arrayListCart.sumOf { it.totalAmount }.toDouble()}.00"

            totalPrice = 0
            totalQuantity = 0

            for (i in itemDetails.variants) {
                totalPrice += i.price * i.quantity
                Log.d("totalPrice", totalPrice.toString())

                totalQuantity += i.quantity
                Log.d("totalQuantity", totalQuantity.toString())

            }

            finalTotalPrice += totalPrice
            finalQuantity += totalQuantity

            Log.d("finalQuantity", finalQuantity.toString())

            ProductCartActivity.orderTotalAmount = finalTotalPrice

            ProductCartActivity.orderTotalQuantity = finalQuantity

            ProductCartActivity.cartInstance.updatedCal()

            DeliveredOrderAdapter.orderTotalAmount = finalTotalPrice

            totalAmountTV.text = "Total Amount-Rs ${totalPrice}.00"
            discountedPriceTV.text = "Rs ${totalPrice}.00"
            actualPriceTV.text = "Rs ${itemDetails.actualPrice}.00"

            Log.d("TAG:totalAmount", "onBindViewHolder: ${(itemDetails.totalAmount)}")

//            updateQty = itemDetails.variants[0].quantity
//          /  quantityShowTV.text = updateQty.toString()
//            sizeBlockTV.text = itemDetails.variants[0].size
//            colorIV.setBackgroundColor(Color.parseColor(itemDetails.variants[0].color))

            Glide.with(context).load(itemDetails.imageUrl).into(imageIV)

/*
            plusQuantityTV.setOnClickListener {

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

            cartColorSizeAdapter = CartColorSizeAdapter(context, itemDetails.variants)
            cartSizeColorRV.adapter = cartColorSizeAdapter
            cartSizeColorRV.isNestedScrollingEnabled = false
            cartSizeColorRV.hasFixedSize()

            Log.d("itemDetails.variants", itemDetails.variants.toString())

            deleteIconIV.setOnClickListener {
                ProductCartActivity.getInstance().updatedCal()

                arrayList.removeAt(position)
                selectedVIDs.removeAt(position)
                sharedPreference.setArray()

                notifyDataSetChanged()

                if (arrayList.isEmpty()) {
                    (context as Activity).finish()
                }
            }
        }

        fun update() {
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    inner class ViewHolder(itemView: ItemYourCartBinding) : RecyclerView.ViewHolder(itemView.root) {

        val binding: ItemYourCartBinding = itemView

    }


}