package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ActivityInvoiceBinding

class InvoiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityInvoiceBinding
    lateinit var sharedPreference: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)

        sharedPreference = SharedPreference(this)

        binding.customerNameTV.text =
            "${sharedPreference.getName()}\n${sharedPreference.getFlat()}, " +
                    "${sharedPreference.getStreet()}," +
                    " ${sharedPreference.getPin()}," +
                    "${sharedPreference.getCity()},${sharedPreference.getLandmark()}"


    }
}