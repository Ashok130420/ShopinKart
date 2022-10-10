package com.example.shopinkarts.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.YourCartAdapter
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityProductCartBinding
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

        fun getInstance(): ProductCartActivity {
            return cartInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_cart)

        cartInstance = this
        DashBoardActivity.arrayListCart

        updatedCal()

        binding.headerProductCart.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.headerProductCart.nameTV.text = resources.getString(R.string.your_cart)

        if (DashBoardActivity.arrayListCart.isNotEmpty()) {

            binding.orderSummaryTV.visibility = View.VISIBLE
            binding.amountCL.visibility = View.VISIBLE
            binding.continueCL.visibility = View.VISIBLE
            binding.continueTV.visibility = View.VISIBLE

        } else {

            binding.orderSummaryTV.visibility = View.GONE
            binding.amountCL.visibility = View.GONE
            binding.continueCL.visibility = View.GONE
            binding.continueTV.visibility = View.GONE

        }

        // adapter for your cart
        yourCartAdapter = YourCartAdapter(this, DashBoardActivity.arrayListCart)
        binding.yourCartRV.adapter = yourCartAdapter
        binding.yourCartRV.isNestedScrollingEnabled = false
        yourCartAdapter.notifyDataSetChanged()

        binding.continueTV.setOnClickListener {
            val intent = Intent(this, PersonalDetailsActivity::class.java)
            intent.putExtra("totalAmount", totalAmount)
            intent.putExtra("gst", gst)
            intent.putExtra("discount", differenceAmount)
            intent.putExtra("finalAmount", amountPaid)

            startActivity(intent)

        }
        Log.d("TAG_totalAmount", "onCreate: $totalAmount")
    }

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


        totalAmount = DashBoardActivity.arrayListCart.sumBy { it.totalAmount }.toDouble()
        binding.totalAmountValueTV.text = "RS $totalAmount"
        Log.d("totalAmount", "onCreate: $totalAmount")

        discountAmount = DashBoardActivity.arrayListCart.sumBy { it.actualPrice }.toFloat()
        binding.discountsValueTV.text = "- RS $discountAmount"
        Log.d("totalDis{countAmount", "onCreate: $discountAmount")

        orderTotal = (totalAmount - discountAmount).toFloat()
        binding.orderTotalValueTV.text = "Rs $orderTotal"
        Log.d("totalOrderTotal", "onCreate: $orderTotal")

        gst = orderTotal * 5 / 100
        binding.gstValueTV.text = "Rs $gst"

        amountPaid = orderTotal + gst
        binding.amountPaidValueTV.text = "Rs $amountPaid"
        Log.d("totalAmountPaid", "onCreate: $amountPaid")

        differenceAmount = (totalAmount - amountPaid).toFloat()

        if (DashBoardActivity.arrayListCart.isNotEmpty()) {
            percentage = differenceAmount / totalAmount * 100
            binding.giftTV.text =
                "Congratulations! You are saving ${percentage.roundToInt()} % on \nthis order"
            Log.d("totalPercentage", "onCreate: ${percentage.roundToInt()}")
        }

    }

}