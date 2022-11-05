package com.app.shopinkarts.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityForgotPasswordBinding

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

    // To remove EditText focus on touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}