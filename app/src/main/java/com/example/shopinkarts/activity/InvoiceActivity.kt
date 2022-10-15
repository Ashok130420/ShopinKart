package com.example.shopinkarts.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.InvoiceAdapter
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityInvoiceBinding
import com.example.shopinkarts.fragments.OrdersFragment
import com.example.shopinkarts.model.CreateProduct

class InvoiceActivity : AppCompatActivity() {

    lateinit var binding: ActivityInvoiceBinding
    lateinit var sharedPreference: SharedPreference
    lateinit var invoiceAdapter: InvoiceAdapter
    var position = 0
    var arrayList: List<CreateProduct> = ArrayList()
    var gstAmount = 0F

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_invoice)

        sharedPreference = SharedPreference(this)

        binding.back.setOnClickListener {
            onBackPressed()
        }

        position = intent.extras!!.getInt("position", 0)

        binding.customerNameTV.text =
            "${sharedPreference.getName()}\n${sharedPreference.getFlat()},${sharedPreference.getStreet()}, ${sharedPreference.getPin()},${sharedPreference.getCity()},${sharedPreference.getLandmark()}"

        binding.paymentTV.text =
            "COD : Collect amount \nRs ${OrdersFragment.arrayListMyOrders[position].totalAmount + OrdersFragment.arrayListMyOrders[position].gstAmount} /-"

        arrayList = OrdersFragment.arrayListMyOrders[position].products

        binding.tAmountValueTV.text =
            OrdersFragment.arrayListMyOrders[position].totalAmount.toString()

        binding.gstTaxValueTV.text = OrdersFragment.arrayListMyOrders[position].gstAmount.toString()

        binding.totalValueTV.text =
            "Rs ${OrdersFragment.arrayListMyOrders[position].totalAmount + OrdersFragment.arrayListMyOrders[position].gstAmount}"

        gstAmount = OrdersFragment.arrayListMyOrders[position].gstAmount

        invoiceAdapter = InvoiceAdapter(this, arrayList, gstAmount)
        binding.invoiceDetailsRV.adapter = invoiceAdapter
        binding.invoiceDetailsRV.hasFixedSize()
        binding.invoiceDetailsRV.isNestedScrollingEnabled = false

    }
}