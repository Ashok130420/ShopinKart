package com.example.shopinkarts.activity

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ActivityCustomerSupportBinding
import com.example.shopinkarts.response.CustomerSupportResponse
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support)

        sharedPreference = SharedPreference(this)

        binding.noTV.setBackgroundResource(R.drawable.button_blue_radius5)
        binding.noTV.setTextColor(ContextCompat.getColor(this,R.color.white))
        binding.orderIdTV.visibility = View.GONE
        binding.returnOrderId.visibility = View.GONE
        Log.d("isReturnRequest", "onCreate: $isReturnRequest")

        binding.headerCustomerSupport.nameTV.text = resources.getString(R.string.customer_support)
        binding.headerCustomerSupport.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.nameET.setText(sharedPreference.getName())
        binding.phoneET.setText(sharedPreference.getPhone())

        binding.noTV.setOnClickListener {

            isReturnRequest = 0
            binding.orderIdTV.visibility = View.GONE
            binding.returnOrderId.visibility = View.GONE
            binding.noTV.setBackgroundResource(R.drawable.button_blue_radius5)
            binding.yesTV.setBackgroundResource(R.drawable.shape_text_grey)
            binding.noTV.setTextColor(ContextCompat.getColor(this,R.color.white))
            binding.yesTV.setTextColor(ContextCompat.getColor(this,R.color.hint_color))

            Log.d("isReturnRequest", "onCreate: $isReturnRequest")
        }

        binding.yesTV.setOnClickListener {

            isReturnRequest = 1
            binding.orderIdTV.visibility = View.VISIBLE
            binding.returnOrderId.visibility = View.VISIBLE
            binding.yesTV.setBackgroundResource(R.drawable.button_blue_radius5)
            binding.noTV.setBackgroundResource(R.drawable.shape_text_grey)
            binding.noTV.setTextColor(ContextCompat.getColor(this,R.color.hint_color))
            binding.yesTV.setTextColor(ContextCompat.getColor(this,R.color.white))
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