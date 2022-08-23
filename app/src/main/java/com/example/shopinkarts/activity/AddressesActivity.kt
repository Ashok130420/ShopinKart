package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityAddressesBinding



class AddressesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddressesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addresses)

        binding.headerAddress.nameTV.text = resources.getString(R.string.addresses)
        binding.headerAddress.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}