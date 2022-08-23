package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.OrderDetailsAdapter
import com.example.shopinkarts.databinding.ActivityOrderDetailsBinding
import com.example.shopinkarts.databinding.BottomSheetReturnOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class OrderDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityOrderDetailsBinding
    lateinit var orderDetailsAdapter: OrderDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_details)

        binding.headerOrderDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerOrderDetails.nameTV.text = resources.getString(R.string.order_details)

        //adapter for order details
        orderDetailsAdapter = OrderDetailsAdapter(this)
        binding.orderDetailsRV.adapter = orderDetailsAdapter
        binding.orderDetailsRV.isNestedScrollingEnabled = false

        binding.orderReturnTV.setOnClickListener {

            val dialog = BottomSheetDialog(this)
            val bindingBottomSheet = DataBindingUtil.inflate<BottomSheetReturnOrderBinding>(
                layoutInflater,
                R.layout.bottom_sheet_return_order,
                null,
                false
            )
            dialog.setContentView(bindingBottomSheet.root)

            bindingBottomSheet.titleTV.text=resources.getString(R.string.return_order)

            bindingBottomSheet.submitRequestTV.setOnClickListener {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Request Send", Toast.LENGTH_LONG).show()

            }

            bindingBottomSheet.backIV.setOnClickListener {

                dialog.dismiss()
            }

            dialog.setCancelable(false)

            dialog.setContentView(bindingBottomSheet.root)

            dialog.show()
        }
    }
}