package com.app.shopinkarts.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityForgotPasswordEmailBinding
import com.app.shopinkarts.response.ForgotPasswordSendOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordPhoneActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordEmailBinding

    var phoneNo = ""
    var otpSend = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password_email)


        binding.headerForgotEmail.nameTV.text = resources.getString(R.string.password_reset)
        binding.headerForgotEmail.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.sendOtpTV.setOnClickListener {

            phoneNo = binding.phoneNoTV.text.toString().trim()

            if (phoneNo.isEmpty()) {
                binding.phoneNoTV.error = "Please enter phone number"
                binding.phoneNoTV.requestFocus()
                return@setOnClickListener
            }

            forgotPasswordSendOtp()

        }

    }

    private fun forgotPasswordSendOtp() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["phone"] = phoneNo

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Please Wait...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

        val call: Call<ForgotPasswordSendOtpResponse> =
            RetrofitClient.instance!!.api.forgotPasswordSendOtp(requestBody)
        call.enqueue(object : Callback<ForgotPasswordSendOtpResponse> {
            override fun onResponse(
                call: Call<ForgotPasswordSendOtpResponse>,
                response: Response<ForgotPasswordSendOtpResponse>
            ) {
                if (response.isSuccessful) {
                    val forgotPasswordSendOtpResponse = response.body()
                    if (forgotPasswordSendOtpResponse!!.status) {
                        mProgressDialog.dismiss()

                        Toast.makeText(
                            this@ForgotPasswordPhoneActivity,
                            forgotPasswordSendOtpResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        otpSend = forgotPasswordSendOtpResponse.otp
                        val intent = Intent(
                            this@ForgotPasswordPhoneActivity, ForgotPasswordActivity::class.java
                        )
                        intent.putExtra("phoneNo", phoneNo)
                        intent.putExtra("otpSend", otpSend)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@ForgotPasswordPhoneActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<ForgotPasswordSendOtpResponse>, t: Throwable) {
                Toast.makeText(
                    this@ForgotPasswordPhoneActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                mProgressDialog.dismiss()
            }

        })
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