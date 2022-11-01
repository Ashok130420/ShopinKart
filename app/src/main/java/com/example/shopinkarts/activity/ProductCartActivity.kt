package com.example.shopinkarts.activity


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.YourCartAdapter
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityProductCartBinding
import com.example.shopinkarts.response.Variants
import kotlin.math.roundToInt

class ProductCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductCartBinding
    lateinit var yourCartAdapter: YourCartAdapter
    var totalAmount: Double = 0.00
    var discountAmount = 0F
    var orderTotal = 0F
    var gst = 0F
    var amountPaid = 0F
    var percentage: Double = 0.00
    var differenceAmount = 0F


    companion object {
        var cartInstance: ProductCartActivity = ProductCartActivity()
        var orderTotalAmount = 0
        var orderTotalQuantity = 0

        var arrayTotal: ArrayList<Int> = ArrayList()
        var productsPrice = 0

        fun getInstance(): ProductCartActivity {
            return cartInstance
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_cart)

        cartInstance = this

        DashBoardActivity.arrayListCart

//        updatedCal()

        binding.headerProductCart.backIV.setOnClickListener {
            finish()
        }

        binding.headerProductCart.nameTV.text = resources.getString(R.string.your_cart)

        if (DashBoardActivity.arrayListCart.isNotEmpty()) {

            binding.orderSummaryTV.visibility = View.VISIBLE
            binding.amountCL.visibility = View.VISIBLE
            binding.continueCL.visibility = View.GONE
            binding.continueTV.visibility = View.VISIBLE

        } else {

            binding.orderSummaryTV.visibility = View.GONE
            binding.amountCL.visibility = View.GONE
            binding.continueCL.visibility = View.GONE
            binding.continueTV.visibility = View.GONE

        }


        // adapter for your cart
        arrayTotal.clear()
        yourCartAdapter = YourCartAdapter(this, DashBoardActivity.arrayListCart)
        binding.yourCartRV.adapter = yourCartAdapter
        binding.yourCartRV.isNestedScrollingEnabled = false
        yourCartAdapter.notifyDataSetChanged()

        Log.d("arrayListCart", DashBoardActivity.arrayListCart.toString())

//        updatedCal()

        binding.continueTV.setOnClickListener {
            val intent = Intent(this, CheckOutDetailsActivity::class.java)
            intent.putExtra("totalAmount", totalAmount)
            intent.putExtra("gst", gst)
            intent.putExtra("discount", differenceAmount)
            intent.putExtra("finalAmount", amountPaid)

            startActivity(intent)

        }
        Log.d("TAG_totalAmount", "onCreate: $totalAmount")
    }

    @SuppressLint("SetTextI18n")
    fun updatedCal() {

        if (DashBoardActivity.arrayListCart.isEmpty()) {
            binding.lottyAnimation.visibility = View.VISIBLE
            binding.yourCartCL.visibility = View.GONE
            binding.orderSummaryTV.visibility = View.GONE
            binding.amountCL.visibility = View.GONE
            binding.continueCL.visibility = View.GONE
            binding.continueTV.visibility = View.GONE
        } else {
            binding.yourCartCL.visibility = View.VISIBLE
            binding.lottyAnimation.visibility = View.GONE
        }

//        totalAmount = DashBoardActivity.arrayListCart.sumOf { it.totalAmount }.toDouble()

//        for (item in DashBoardActivity.arrayListCart) {

//            totalAmount = item.variants.sumOf { it.price }.toDouble()


//            discountAmount = DashBoardActivity.arrayListCart.sumOf { it.actualPrice }.toFloat()
//            binding.discountsValueTV.text = "- RS $discountAmount"
//            Log.d("totalDis{countAmount", "onCreate: $discountAmount")
//
//            orderTotal = (totalAmount - discountAmount).toFloat()
//            binding.orderTotalValueTV.text = "Rs $orderTotal"
//            Log.d("totalOrderTotal", "onCreate: $orderTotal")

        productsPrice= arrayTotal.sum()
        Log.d("productsPrice", productsPrice.toString())

        binding.totalAmountValueTV.text = "RS $productsPrice.0"
        Log.d("totalAmount", "onCreate: $orderTotalAmount")
        gst = (productsPrice * 5 / 100).toFloat()
        binding.gstValueTV.text = "Rs $gst"

        amountPaid = orderTotalAmount + gst
        binding.amountPaidValueTV.text = "Rs $amountPaid"
        Log.d("totalAmountPaid", "onCreate: $amountPaid")

        differenceAmount = (totalAmount - amountPaid).toFloat()


        /*  binding.totalAmountValueTV.text = "RS ${YourCartAdapter.totalPrice}"
          Log.d("totalAmount", "onCreate: $orderTotalAmount")
          gst = (YourCartAdapter.totalPrice * 5 / 100).toFloat()
          binding.gstValueTV.text = "Rs $gst"

          amountPaid = YourCartAdapter.totalPrice + gst
          binding.amountPaidValueTV.text = "Rs $amountPaid"
          Log.d("totalAmountPaid", "onCreate: $amountPaid")

          differenceAmount = (YourCartAdapter.totalPrice - amountPaid).toFloat()*/

//            if (DashBoardActivity.arrayListCart.isNotEmpty()) {
//                percentage = differenceAmount / totalAmount * 100
//                binding.giftTV.text =
//                    "Congratulations! You are saving ${percentage.roundToInt()} % on \nthis order"
//                Log.d("totalPercentage", "onCreate: ${percentage.roundToInt()}")
//            }
    }

    //    }
    fun update() {
        yourCartAdapter.notifyDataSetChanged()
    }

}