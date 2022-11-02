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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.MyApplication
import com.example.shopinkarts.api.ONESIGNAL_APP_ID
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityLoginBinding
import com.example.shopinkarts.response.LoginResponse
import com.onesignal.OneSignal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreference: SharedPreference
    var deviceId = String()
    var phone = ""
    var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        sharedPreference = SharedPreference(this)

        deviceId = OneSignal.getDeviceState()?.userId.toString()

        Log.d("TAG_deviceId", "onCreate: $deviceId")

        binding.signUpTV.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.signInTv.setOnClickListener {

            phone = binding.phoneET.text.toString().trim()
            password = binding.passwordET.text.toString().trim()

            if (phone.isEmpty()) {
                binding.phoneET.error = "Enter phone number"
                binding.phoneET.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordET.error = "Enter password"
                binding.passwordET.requestFocus()
                return@setOnClickListener
            }

            loginApi()
        }

        binding.forgotPasswordTV.setOnClickListener {
            val intent = Intent(this, ForgotPasswordEmailActivity::class.java)
            startActivity(intent)
        }

    }

    // Password hide and show
    fun passToggle(view: View) {
        binding.imageToggle.setOnClickListener(object : View.OnClickListener {
            var button01pos = 0
            override fun onClick(v: View?) {
                if (button01pos == 0) {
                    binding.imageToggle.setImageResource(R.drawable.eye_close)
                    button01pos = 1
                    binding.passwordET.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.passwordET.setSelection(binding.passwordET.length())
                } else if (button01pos == 1) {
                    binding.imageToggle.setImageResource(R.drawable.eye_open)
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

    private fun loginApi() {

        deviceId = OneSignal.getDeviceState()?.userId.toString()
        Log.d("TAG_deviceId", "onCreate: $deviceId")
        val requestBody: MutableMap<String, String> = HashMap()

        requestBody["phone"] = phone
        requestBody["password"] = password
        requestBody["deviceId"] = deviceId


        /*Progress bar*/
        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Loading....")
        mProgressDialog.show()

        val call: Call<LoginResponse> = RetrofitClient.instance!!.api.login(requestBody)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    mProgressDialog.dismiss()
                    val loginResponse = response.body()
                    if (loginResponse!!.status) {

                        sharedPreference.setToken(loginResponse.token)
                        sharedPreference.setUserId(loginResponse.user._id)
                        sharedPreference.isLoginSet(loginResponse.status)
                        sharedPreference.setUsertype(loginResponse.user.userType.toString())

                        sharedPreference.setPhoneNo(phoneNo = binding.phoneET.text.toString())

                        val intent = Intent(this@LoginActivity, DashBoardActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        Toast.makeText(
                            this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT
                        ).show()
                        Log.e("TAG", "${response.message()} ")
                    }

                } else {
                    mProgressDialog.dismiss()
                    Toast.makeText(
                        this@LoginActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                }
                mProgressDialog.dismiss()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "${t.message} ")
            }
        })

    }
}