package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityOtpVerifyBinding

class OtpVerifyActivity : AppCompatActivity() {
    lateinit var binding: ActivityOtpVerifyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verify)

        binding.headerOtpVerify.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}