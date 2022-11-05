package com.app.shopinkarts.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivitySignUpBinding
import com.app.shopinkarts.response.VerifyOtpResponse
import com.onesignal.OneSignal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    private lateinit var sharedPreference: SharedPreference

    var phone = ""
    var email = ""
    var userType = String()
    var password = ""
    var deviceId = String()
    var gst = ""

    var verifyOtpSend = ""


    companion object {
        var mInstance: SignUpActivity = SignUpActivity()
        fun getInstance(): SignUpActivity {
            return mInstance
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        sharedPreference = SharedPreference(this)

        binding.gstTV.visibility = View.GONE
        binding.gstValueET.visibility = View.GONE

        deviceId = OneSignal.getDeviceState()?.userId.toString()
        Log.d("TAG_deviceId", "onCreate: $deviceId")

        binding.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById<RadioButton>(i)
            if (rb != null) userType = radioGroup.indexOfChild(rb).toString()

        }

        binding.personalRB.setOnClickListener {
            binding.gstTV.visibility = View.GONE
            binding.gstValueET.visibility = View.GONE
        }

        binding.resellingRB.setOnClickListener {
            binding.gstTV.visibility = View.VISIBLE
            binding.gstValueET.visibility = View.VISIBLE
        }

        binding.signUpTv.setOnClickListener {

            phone = binding.phoneET.text.toString().trim()
            email = binding.emailET.text.toString().trim()
            password = binding.passwordET.text.toString().trim()
            gst = binding.gstValueET.text.toString().trim()

            if (phone.isEmpty()) {
                binding.phoneET.error = "Enter phone number"
                binding.phoneET.requestFocus()
                return@setOnClickListener
            }
            /* if (email.isEmpty()) {
                 binding.emailET.error = "Enter email address"
                 binding.emailET.requestFocus()
                 return@setOnClickListener
             }*/
            /*   if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                   binding.emailET.error = "Enter valid email"
                   binding.emailET.requestFocus()
                   return@setOnClickListener
               }*/
            if (password.isEmpty()) {
                binding.passwordET.error = "Enter password"
                binding.passwordET.requestFocus()
                return@setOnClickListener
            }
            if (password.length < 6) {
                binding.passwordET.error = "Minimum password length 6 "
                binding.passwordET.requestFocus()
                return@setOnClickListener
            }


//            signUpApi()
            verifyOtp()
        }

    }

    // Password hide and show
    fun passToggle(view: View) {
        binding.passToggle.setOnClickListener(object : View.OnClickListener {
            var button01pos = 0
            override fun onClick(v: View?) {
                if (button01pos == 0) {
                    binding.passToggle.setImageResource(R.drawable.eye_close)
                    button01pos = 1
                    binding.passwordET.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.passwordET.setSelection(binding.passwordET.length())
                } else if (button01pos == 1) {
                    binding.passToggle.setImageResource(R.drawable.eye_open)
                    button01pos = 0
                    binding.passwordET.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.passwordET.setSelection(binding.passwordET.length())
                }
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

    /* fun signUpApi() {
         val requestBody: MutableMap<String, String> = HashMap()
         requestBody["phone"] = phone
         requestBody["email"] = email
         requestBody["password"] = password
         requestBody["deviceId"] = deviceId
         requestBody["userType"] = userType

 //        val mProgressDialog = ProgressDialog(this)
 //        mProgressDialog.setMessage("Please wait....")
 //        mProgressDialog.setCanceledOnTouchOutside(false)
 //        mProgressDialog.show()

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

                         sharedPreference.setPhoneNo(phoneNo = binding.phoneET.text.toString())
                         sharedPreference.setGst(gst = binding.gstValueET.text.toString())

                         Toast.makeText(
                             this@SignUpActivity, signupResponse.message, Toast.LENGTH_SHORT
                         ).show()
                         val intent = Intent(this@SignUpActivity, DashBoardActivity::class.java)
                         intent.flags =
                             Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
 //                        val intent = Intent(this@SignUpActivity, OtpVerifyActivity::class.java)
                         startActivity(intent)
                     }

                 } else {
 //                    mProgressDialog.dismiss()
                     Toast.makeText(
                         this@SignUpActivity, response.message(), Toast.LENGTH_SHORT
                     ).show()
                 }
 //                mProgressDialog.dismiss()
             }

             override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                 Toast.makeText(
                     this@SignUpActivity, "${t.message}", Toast.LENGTH_SHORT
                 ).show()
             }

         })
     }*/

    private fun verifyOtp() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["phone"] = phone

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Please Wait...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

        val call: Call<VerifyOtpResponse> = RetrofitClient.instance!!.api.verifyOtp(requestBody)
        call.enqueue(object : Callback<VerifyOtpResponse> {
            override fun onResponse(
                call: Call<VerifyOtpResponse>, response: Response<VerifyOtpResponse>
            ) {
                if (response.isSuccessful) {
                    val verifyOtpResponse = response.body()
                    if (verifyOtpResponse!!.status) {
                        mProgressDialog.dismiss()

                        Toast.makeText(
                            this@SignUpActivity,
                            verifyOtpResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        verifyOtpSend = verifyOtpResponse.otp
                        Log.d("verifyOtpSend", verifyOtpSend)

                        val intent = Intent(this@SignUpActivity, OtpVerifyActivity::class.java)

                        intent.putExtra("verifyOtpSend", verifyOtpSend)
                        intent.putExtra("phone", phone)
                        intent.putExtra("email", email)
                        intent.putExtra("password", password)
                        intent.putExtra("deviceId", deviceId)
                        intent.putExtra("userType", userType)
                        intent.putExtra("gst", gst)
//                        intent.flags =
//                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@SignUpActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<VerifyOtpResponse>, t: Throwable) {
                Toast.makeText(
                    this@SignUpActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                mProgressDialog.dismiss()
            }

        })
    }
}