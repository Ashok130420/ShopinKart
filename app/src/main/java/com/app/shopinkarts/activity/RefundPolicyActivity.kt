package com.app.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityRefundPolicyBinding

class RefundPolicyActivity : AppCompatActivity() {
    lateinit var binding: ActivityRefundPolicyBinding
    var header=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refund_policy)

        header = intent.extras!!.getString("header", "")
        binding.headerAbout.nameTV.text = header

        binding.headerAbout.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}