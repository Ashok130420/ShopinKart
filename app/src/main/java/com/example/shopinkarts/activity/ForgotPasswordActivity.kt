package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        binding.headerForgotPassword.nameTV.text = resources.getString(R.string.password_reset)
        binding.headerForgotPassword.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.submitTV.setOnClickListener {
            val intent = Intent(this, NewPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.changeTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordEmailActivity::class.java)
            startActivity(intent)
        }

    }
}