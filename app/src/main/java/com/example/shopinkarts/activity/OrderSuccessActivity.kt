package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityOrderSuccessBinding
import com.example.shopinkarts.fragments.OrdersFragment


class OrderSuccessActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderSuccessBinding
    var ordersFragment = OrdersFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_success)
        ordersFragment = OrdersFragment()
        binding.headerOrderDetails.nameTV.text = resources.getString(R.string.order_success)
        binding.headerOrderDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.continueShoppingTV.setOnClickListener {
            val intent = Intent(this, DashBoardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.goToOrdersTV.setOnClickListener {

            val intent = Intent(this, DashBoardActivity::class.java)
            intent.putExtra("from", "myOrder")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }

        val ss = SpannableString(
            "You can track the delivery in the \n" +
                    "\"Order\" section"
        )
        val span1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {

                val intent = Intent(this@OrderSuccessActivity, TrackOrderActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@OrderSuccessActivity, "clicked", Toast.LENGTH_SHORT).show()
            }
        }

        ss.setSpan(span1, 36, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.trackDeliveryTV.text = ss
        binding.trackDeliveryTV.highlightColor = ContextCompat.getColor(this, R.color.primary_Blue)
        binding.trackDeliveryTV.movementMethod = LinkMovementMethod.getInstance()

    }

}