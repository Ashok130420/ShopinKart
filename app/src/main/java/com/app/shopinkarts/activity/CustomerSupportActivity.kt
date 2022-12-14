package com.app.shopinkarts.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityCustomerSupportBinding
import com.app.shopinkarts.response.CustomerSupportResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomerSupportActivity : AppCompatActivity() {

    lateinit var sharedPreference: SharedPreference
    lateinit var binding: ActivityCustomerSupportBinding
    var TAG = "CustomerSupportActivity"
    var phone = ""
    var message = ""
    var isReturnRequest = 0
    var userType = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support)

        sharedPreference = SharedPreference(this)
        userType = sharedPreference.getUserType().toString()

        binding.noTV.setBackgroundResource(R.drawable.button_blue_radius5)
        binding.noTV.setTextColor(ContextCompat.getColor(this, R.color.white))
        binding.orderIdTV.visibility = View.GONE
        binding.returnOrderId.visibility = View.GONE
        Log.d("isReturnRequest", "onCreate: $isReturnRequest")

        binding.headerCustomerSupport.nameTV.text = resources.getString(R.string.customer_support)
        binding.headerCustomerSupport.backIV.setOnClickListener {
            onBackPressed()
        }

        if (userType == "0") {
            binding.nameET.setText(sharedPreference.getName())
            binding.phoneET.setText(sharedPreference.getPhone())
        } else {
            binding.nameET.setText(sharedPreference.getBusinessName())
            binding.phoneET.setText(sharedPreference.getBusinessPhoneNo())
        }


        binding.noTV.setOnClickListener {

            isReturnRequest = 0
            binding.orderIdTV.visibility = View.GONE
            binding.returnOrderId.visibility = View.GONE
            binding.noTV.setBackgroundResource(R.drawable.button_blue_radius5)
            binding.yesTV.setBackgroundResource(R.drawable.shape_text_grey)
            binding.noTV.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.yesTV.setTextColor(ContextCompat.getColor(this, R.color.hint_color))

            Log.d("isReturnRequest", "onCreate: $isReturnRequest")
        }

        binding.yesTV.setOnClickListener {

            isReturnRequest = 1
            binding.orderIdTV.visibility = View.VISIBLE
            binding.returnOrderId.visibility = View.VISIBLE
            binding.yesTV.setBackgroundResource(R.drawable.button_blue_radius5)
            binding.noTV.setBackgroundResource(R.drawable.shape_text_grey)
            binding.noTV.setTextColor(ContextCompat.getColor(this, R.color.hint_color))
            binding.yesTV.setTextColor(ContextCompat.getColor(this, R.color.white))
            Log.d("isReturnRequest", "onCreate: $isReturnRequest")
        }


        binding.submitTV.setOnClickListener {

            phone = binding.phoneET.text.toString()
            message = binding.messageET.text.toString()

            if (phone.isEmpty()) {
                binding.phoneET.error = "Please enter phone no"
                binding.phoneET.requestFocus()
                return@setOnClickListener
            }
            if (message.isEmpty()) {
                binding.messageET.error = "Please enter your message here"
                binding.messageET.requestFocus()
                return@setOnClickListener
            }

            customerSupport()
        }
    }

    private fun customerSupport() {

        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["fullName"] = binding.nameET.text.toString()
        requestBody["phone"] = phone
        requestBody["productId"] = binding.productIdET.text.toString()
        requestBody["message"] = message
        requestBody["isReturnRequest"] = isReturnRequest.toString()
        requestBody["orderId"] = binding.returnOrderId.text.toString()

        val call: Call<CustomerSupportResponse> =
            RetrofitClient.instance!!.api.customerSupportApi(requestBody)
        call.enqueue(object : Callback<CustomerSupportResponse> {
            override fun onResponse(
                call: Call<CustomerSupportResponse>, response: Response<CustomerSupportResponse>
            ) {
                val customerResponse = response.body()
                if (response.isSuccessful) {

                    Toast.makeText(
                        this@CustomerSupportActivity, customerResponse?.message, Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onResponse: ${customerResponse?.message}")
                    val intent = Intent(this@CustomerSupportActivity, DashBoardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                } else {

                    Toast.makeText(
                        this@CustomerSupportActivity, response.message(), Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CustomerSupportResponse>, t: Throwable) {
                Toast.makeText(
                    this@CustomerSupportActivity, t.message, Toast.LENGTH_SHORT
                ).show()
                Log.d(TAG, "onResponse: ${t.message}")
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