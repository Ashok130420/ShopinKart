package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityCustomerSupportBinding



class CustomerSupportActivity : AppCompatActivity() {
    lateinit var binding: ActivityCustomerSupportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support)

        binding.headerCustomerSupport.nameTV.text = resources.getString(R.string.customer_support)
        binding.headerCustomerSupport.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}