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
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityOtpVerifyBinding
import com.example.shopinkarts.response.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerifyActivity : AppCompatActivity() {

    lateinit var binding: ActivityOtpVerifyBinding
    lateinit var sharedPreference: SharedPreference

    private var verifyOtpSend = ""
    private var phone = ""
    private var email = ""
    private var password = ""
    private var deviceId = ""
    private var userType = ""
    private var gst = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verify)

        sharedPreference = SharedPreference(this)

        verifyOtpSend = intent.extras!!.getString("verifyOtpSend", "")
        phone = intent.extras!!.getString("phone", "")
        email = intent.extras!!.getString("email", "")
        password = intent.extras!!.getString("password", "")
        deviceId = intent.extras!!.getString("deviceId", "")
        userType = intent.extras!!.getString("userType", "")
        gst = intent.extras!!.getString("gst", "")

        Log.d("verifyOtpSend", verifyOtpSend)
        Log.d("verifyOtpSend", phone)
        Log.d("verifyOtpSend", email)
        Log.d("verifyOtpSend", password)
        Log.d("verifyOtpSend", deviceId)
        Log.d("verifyOtpSend", userType)


        binding.headerOtpVerify.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.submitTV.setOnClickListener {

            if (verifyOtpSend == binding.firstPinView.text.toString()) {

                signUpApi()

//                val intent = Intent(this, DashBoardActivity::class.java)
////                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(intent)
                Log.d("forgotPasswordSendOtp", "true")
            } else {
                Log.d(
                    "forgotPasswordSendOtp", "false  $verifyOtpSend == ${binding.firstPinView.text}"
                )
                Toast.makeText(this, "Invalid otp", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun signUpApi() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["phone"] = phone
        requestBody["email"] = email
        requestBody["password"] = password
        requestBody["deviceId"] = deviceId
        requestBody["userType"] = userType

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Please wait....")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

        val call: Call<SignUpResponse> = RetrofitClient.instance!!.api.signup(requestBody)
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>, response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    val signupResponse = response.body()
                    if (signupResponse!!.status) {
//                        mProgressDialog.dismiss()

                        sharedPreference.setToken(signupResponse.token)
                        sharedPreference.setUserId(signupResponse.user._id)

                        Log.d("Token....", signupResponse.token)
                        sharedPreference.setUsertype(signupResponse.user.userType.toString())
                        Log.d("UserType", signupResponse.user.userType.toString())

                        Log.d("sharedPreference....", "$sharedPreference")
                        sharedPreference.isLoginSet(signupResponse.status)

                        sharedPreference.setPhoneNo(phoneNo = phone)
                        sharedPreference.setGst(gst = gst)

                        Toast.makeText(
                            this@OtpVerifyActivity, signupResponse.message, Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@OtpVerifyActivity, DashBoardActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//                        val intent = Intent(this@SignUpActivity, OtpVerifyActivity::class.java)
                        startActivity(intent)
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@OtpVerifyActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Toast.makeText(
                    this@OtpVerifyActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}