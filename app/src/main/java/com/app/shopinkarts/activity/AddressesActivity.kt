package com.app.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityAddressesBinding



class AddressesActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddressesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addresses)

        binding.headerAddress.nameTV.text = resources.getString(R.string.addresses)
        binding.headerAddress.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}