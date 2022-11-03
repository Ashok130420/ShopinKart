package com.example.shopinkarts.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityForgotPasswordEmailBinding
import com.example.shopinkarts.response.ForgotPasswordSendOtpResponse
import com.example.shopinkarts.response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordPhoneActivity : AppCompatActivity() {

    lateinit var binding: ActivityForgotPasswordEmailBinding

    var phoneNo = ""
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
        mProgressDialog.setMessage("Loading....")
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

                        val intent = Intent(
                            this@ForgotPasswordPhoneActivity, ForgotPasswordActivity::class.java
                        )
                        intent.putExtra("phoneNo", phoneNo)
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

}