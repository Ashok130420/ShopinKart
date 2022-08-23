package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityRefundPolicyBinding

class RefundPolicyActivity : AppCompatActivity() {
    lateinit var binding: ActivityRefundPolicyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_policy)

        binding.headerRefundPolicy.nameTV.text = resources.getString(R.string.refund_policy)
        binding.headerRefundPolicy.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}