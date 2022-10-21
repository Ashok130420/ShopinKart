package com.example.shopinkarts.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivitySignUpBinding
import com.example.shopinkarts.response.SignUpResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        sharedPreference = SharedPreference(this)

        deviceId = OneSignal.getDeviceState()?.userId.toString()
        Log.d("TAG_deviceId", "onCreate: $deviceId")

        binding.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            val rb = radioGroup.findViewById<RadioButton>(i)
            if (rb != null)
                userType = radioGroup.indexOfChild(rb).toString()

        }

        binding.signUpTv.setOnClickListener {

            phone = binding.phoneET.text.toString().trim()
            email = binding.emailET.text.toString().trim()
            password = binding.passwordET.text.toString().trim()


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
            signUpApi()
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

    fun signUpApi() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["phone"] = phone
        requestBody["email"] = email
        requestBody["password"] = password
        requestBody["deviceId"] = deviceId
        requestBody["userType"] = userType

        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading....")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()

        val call: Call<SignUpResponse> = RetrofitClient.instance!!.api.signup(requestBody)
        call.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                if (response.isSuccessful) {
                    val signupResponse = response.body()
                    if (signupResponse!!.status) {
                        mProgressDialog.dismiss()

                        sharedPreference.setToken(signupResponse.token)
                        sharedPreference.setUserId(signupResponse.user._id)

                        Log.d("Token....", signupResponse.token)
                        sharedPreference.setUsertype(signupResponse.user.userType.toString())
                        Log.d("UserType", signupResponse.user.userType.toString())

                        Log.d("sharedPreference....", "$sharedPreference")
                        sharedPreference.isLoginSet(signupResponse.status)

                        Toast.makeText(
                            this@SignUpActivity,
                            signupResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this@SignUpActivity, DashBoardActivity::class.java)
//                        val intent = Intent(this@SignUpActivity, OtpVerifyActivity::class.java)
                        startActivity(intent)
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@SignUpActivity,
                        response.message(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Toast.makeText(
                    this@SignUpActivity,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}