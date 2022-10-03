package com.example.shopinkarts.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivityCustomerSupportBinding
import com.example.shopinkarts.response.CustomerSupportResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CustomerSupportActivity : AppCompatActivity() {

    lateinit var binding: ActivityCustomerSupportBinding
    var TAG = "CustomerSupportActivity"
    var phone = ""
    var message = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_customer_support)

        binding.headerCustomerSupport.nameTV.text = resources.getString(R.string.customer_support)
        binding.headerCustomerSupport.backIV.setOnClickListener {
            onBackPressed()
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

}