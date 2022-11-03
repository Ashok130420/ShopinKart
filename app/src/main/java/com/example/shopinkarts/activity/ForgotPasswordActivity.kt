package com.example.shopinkarts.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordBinding
    var phoneNo = ""
    var otpSend = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        phoneNo = intent.extras!!.getString("phoneNo", "")
        otpSend = intent.extras!!.getString("otpSend", "")

        binding.headerForgotPassword.nameTV.text = resources.getString(R.string.password_reset)

        binding.emailTV.text =
            "A code is sent to your phone no $phoneNo Please enter the code below to continue"
        binding.headerForgotPassword.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.submitTV.setOnClickListener {

            if (otpSend == binding.pinView.text.toString()) {
                val intent = Intent(this, NewPasswordActivity::class.java)
                intent.putExtra("phoneNo",phoneNo)
                startActivity(intent)
                Log.d("forgotPasswordSendOtp", "true")
            } else {
                Log.d("forgotPasswordSendOtp", "false  $otpSend == ${binding.pinView.text}")
                Toast.makeText(this, "Invalid otp", Toast.LENGTH_LONG).show()
            }

        }
        binding.changeTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordPhoneActivity::class.java)
            startActivity(intent)
        }

    }
}