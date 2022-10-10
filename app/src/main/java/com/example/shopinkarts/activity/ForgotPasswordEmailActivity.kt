package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityForgotPasswordEmailBinding

class ForgotPasswordEmailActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_email)


        binding.headerForgotEmail.nameTV.text = resources.getString(R.string.password_reset)
        binding.headerForgotEmail.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.sendOtpTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }
}