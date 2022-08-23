package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.YourCartAdapter
import com.example.shopinkarts.databinding.ActivityProductCartBinding

class ProductCartActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductCartBinding
    lateinit var yourCartAdapter: YourCartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_cart)

        binding.headerProductCart.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerProductCart.nameTV.text = resources.getString(R.string.your_cart)

        // adapter for your cart
        yourCartAdapter = YourCartAdapter(this)
        binding.yourCartRV.adapter = yourCartAdapter
        binding.yourCartRV.isNestedScrollingEnabled = false

        binding.continueTV.setOnClickListener {
            val intent = Intent(this, PersonalDetailsActivity::class.java)
            startActivity(intent)
        }
        binding.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
        binding.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)

        binding.cashOnDeliveryCL.setOnClickListener {
            binding.caseSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
        }
        binding.payOnlineCL.setOnClickListener {
            binding.onlineSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
        }

    }

    fun selectPayment(view: View) {

        binding.caseSelectIV.setOnClickListener(object : View.OnClickListener {
            var button01pos = 0
            override fun onClick(v: View?) {
                if (button01pos == 0) {
                    binding.caseSelectIV.setImageResource(R.drawable.grey_right_icon)
                    button01pos = 1
                    binding.cashOnDeliveryCL.resources.getColor(R.color.white)
//                    binding.editTextPassword.transformationMethod =
//                        HideReturnsTransformationMethod.getInstance()
//                    binding.editTextPassword.setSelection(binding.editTextPassword.length())
                } else if (button01pos == 1) {
                    binding.caseSelectIV.setImageResource(R.drawable.green_right_icon)
                    button01pos = 1
                    binding.cashOnDeliveryCL.resources.getColor(R.color.light_green)
//                    binding.editTextPassword.transformationMethod =
//                        PasswordTransformationMethod.getInstance()
//                    binding.editTextPassword.setSelection(binding.editTextPassword.length())
                }
            }
        })
    }
}