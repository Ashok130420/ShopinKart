package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.MyTransactionAdapter
import com.example.shopinkarts.databinding.ActivityMyTransactionBinding

class MyTransactionActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyTransactionBinding
    lateinit var myTransactionAdapter: MyTransactionAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_transaction)

        binding.headerMyTransactions.nameTV.text = resources.getString(R.string.my_transactions)
        binding.headerMyTransactions.backIV.setOnClickListener {
            onBackPressed()
        }

        // adapter for my transactions
        myTransactionAdapter = MyTransactionAdapter(this)
        binding.myTransactionRV.adapter = myTransactionAdapter

    }
}