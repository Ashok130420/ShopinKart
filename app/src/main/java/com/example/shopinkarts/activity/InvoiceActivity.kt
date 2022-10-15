package com.example.shopinkarts.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.DeliveredOrderAdapter
import com.example.shopinkarts.adapter.InvoiceAdapter
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ActivityInvoiceBinding
import com.example.shopinkarts.fragments.OrdersFragment
import com.example.shopinkarts.response.Order

class InvoiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityInvoiceBinding
    lateinit var sharedPreference: SharedPreference
    lateinit var invoiceAdapter: InvoiceAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)

        sharedPreference = SharedPreference(this)

        binding.customerNameTV.text = "${sharedPreference.getName()}\n${sharedPreference.getFlat()},${sharedPreference.getStreet()}, ${sharedPreference.getPin()},${sharedPreference.getCity()},${sharedPreference.getLandmark()}"


        invoiceAdapter = InvoiceAdapter(this, DeliveredOrderAdapter.arrayListInvoice)
        binding.invoiceDetailsRV.adapter = invoiceAdapter
        binding.invoiceDetailsRV.hasFixedSize()
        binding.invoiceDetailsRV.isNestedScrollingEnabled = false

    }
}