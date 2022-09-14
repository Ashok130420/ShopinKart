package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.YourCartAdapter
import com.example.shopinkarts.databinding.ActivityProductCartBinding
import com.example.shopinkarts.model.CartModel

class ProductCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductCartBinding
    lateinit var yourCartAdapter: YourCartAdapter
    var arrayListCart: ArrayList<CartModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_cart)

        DashBoardActivity.arrayListCart

        binding.headerProductCart.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerProductCart.nameTV.text = resources.getString(R.string.your_cart)

        if (DashBoardActivity.arrayListCart.isNotEmpty()){
            binding.orderSummaryTV.visibility=View.VISIBLE
            binding.amountCL.visibility=View.VISIBLE
            binding.continueCL.visibility=View.VISIBLE
            binding.continueTV.visibility=View.VISIBLE
        }else{
            binding.orderSummaryTV.visibility=View.GONE
            binding.amountCL.visibility=View.GONE
            binding.continueCL.visibility=View.GONE
            binding.continueTV.visibility=View.GONE
        }

        // adapter for your cart
        yourCartAdapter = YourCartAdapter(this,  DashBoardActivity.arrayListCart)
        binding.yourCartRV.adapter = yourCartAdapter
        binding.yourCartRV.isNestedScrollingEnabled = false


        binding.continueTV.setOnClickListener {
            val intent = Intent(this, PersonalDetailsActivity::class.java)
            startActivity(intent)
        }


    }


}