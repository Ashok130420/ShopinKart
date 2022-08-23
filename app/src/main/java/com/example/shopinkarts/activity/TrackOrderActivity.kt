package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityTrackOrderBinding
import com.example.shopinkarts.databinding.BottomSheetReturnOrderBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class TrackOrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_order)

        binding.headerTrackOrder.nameTV.text = resources.getString(R.string.track_order)
        binding.headerTrackOrder.backIV.setOnClickListener {
            onBackPressed()
        }

        val cancelOrderTV = findViewById<TextView>(R.id.cancelOrderTV)
        cancelOrderTV.setOnClickListener {

            val dialog = BottomSheetDialog(this)
            val bindingBottomSheet = DataBindingUtil.inflate<BottomSheetReturnOrderBinding>(
                layoutInflater,
                R.layout.bottom_sheet_return_order,
                null,
                false
            )

            dialog.setContentView(bindingBottomSheet.root)

            bindingBottomSheet.titleTV.text = resources.getString(R.string.cancel_order)

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