package com.app.shopinkarts.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityTrackOrderBinding
import com.app.shopinkarts.databinding.BottomSheetReturnOrderBinding
import com.app.shopinkarts.response.CancelOrderResponse
import com.app.shopinkarts.response.TrackOrder
import com.app.shopinkarts.response.TrackOrderResponse
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TrackOrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackOrderBinding
    var arrayListTrack: ArrayList<TrackOrder> = ArrayList()
    var orderId = ""

    var reasonCancel = ""
    var reason = ""
    var descriptionCancel = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_track_order)

        orderId = intent.extras!!.getString("orderId", "")

        trackOrder()

        binding.headerTrackOrder.nameTV.text = "Track Order #${orderId}"
        binding.headerTrackOrder.backIV.setOnClickListener {
            finish()
        }

        val cancelOrderTV = findViewById<TextView>(R.id.cancelOrderTV)
        cancelOrderTV.setOnClickListener {

            val dialog = BottomSheetDialog(this)
            val bindingBottomSheet = DataBindingUtil.inflate<BottomSheetReturnOrderBinding>(
                layoutInflater, R.layout.bottom_sheet_return_order, null, false
            )

            dialog.setContentView(bindingBottomSheet.root)

            bindingBottomSheet.titleTV.text = resources.getString(R.string.cancel_order)

            bindingBottomSheet.radioGroupReturnOrder.setOnCheckedChangeListener { radioGroup, i ->
                val rb = radioGroup.findViewById<RadioButton>(i)
                if (rb != null) reasonCancel = radioGroup.indexOfChild(rb).toString()

                val index = radioGroup.indexOfChild(rb)
                when (index) {
                    0 -> {
                        reason = "Damage/Defective"
                    }
                    1 -> {
                        reason = "Quality Not Good"
                    }
                    2 -> {
                        reason = "Size Issue"
                    }
                    3 -> {
                        reason = "Others"
                    }

                }

                Log.d("reasonCancel", reasonCancel)
            }

            bindingBottomSheet.submitRequestTV.setOnClickListener {

                Toast.makeText(this, "Request Send", Toast.LENGTH_LONG).show()

                descriptionCancel = bindingBottomSheet.descriptionET.text.toString().trim()

                Log.d("reasonCancel", reasonCancel)
                Log.d("reasonCancel", reason)
                Log.d("reasonCancel", descriptionCancel)

                cancelOrder()

                dialog.dismiss()
            }

            bindingBottomSheet.backIV.setOnClickListener {

                dialog.dismiss()
            }

            dialog.setCancelable(false)

            dialog.setContentView(bindingBottomSheet.root)

            dialog.show()
        }
    }

    private fun trackOrder() {
        val call: Call<TrackOrderResponse> = RetrofitClient.instance!!.api.trackOrder(id = orderId)
        call.enqueue(object : Callback<TrackOrderResponse> {

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<TrackOrderResponse>, response: Response<TrackOrderResponse>
            ) {
                val trackOrderResponse = response.body()
                if (response.isSuccessful) {
                    if (trackOrderResponse!!.status) {
                        arrayListTrack.clear()
                        arrayListTrack.addAll(trackOrderResponse.order)
                        for (index in arrayListTrack.indices) {
                            when (index) {
                                0 -> {

                                    binding.orderPlacedTimeTV.text = " ${arrayListTrack[0].time} AM"
                                    binding.orderPlacedTV.text = arrayListTrack[0].title
                                    binding.orderPlacedDescriptionTV.text =
                                        arrayListTrack[0].message

                                }
                                1 -> {
                                    val processingTimeTV =
                                        findViewById<TextView>(R.id.processingTimeTV)
                                    val processingTV = findViewById<TextView>(R.id.processingTV)
                                    val processingDescriptionTV =
                                        findViewById<TextView>(R.id.processingDescriptionTV)
                                    val processingIconIV =
                                        findViewById<ImageView>(R.id.processingIconIV)

                                    processingTimeTV.text = " ${arrayListTrack[1].time} AM"
                                    processingTV.text = arrayListTrack[1].title
                                    processingDescriptionTV.text = arrayListTrack[1].message
                                    processingIconIV.setBackgroundResource(R.drawable.green_right_icon)

                                }
                                2 -> {

                                    binding.outForDeliveryDateTV.text =
                                        " ${arrayListTrack[2].time} AM"
                                    binding.outForDeliveryTV.text = arrayListTrack[2].title
                                    binding.outForDeliveryDescriptionTV.text =
                                        arrayListTrack[2].message
                                    binding.outForDeliveryIconIV.setBackgroundResource(R.drawable.green_right_icon)

                                }
                                3 -> {

                                    val deliveredDateTV =
                                        findViewById<TextView>(R.id.deliveredDateTV)
                                    val deliveredTV = findViewById<TextView>(R.id.deliveredTV)
                                    val deliveredDescriptionTV =
                                        findViewById<TextView>(R.id.deliveredDescriptionTV)
                                    val deliveredIconIV =
                                        findViewById<ImageView>(R.id.deliveredIconIV)

                                    deliveredDateTV.text = " ${arrayListTrack[3].time} AM"
                                    deliveredTV.text = arrayListTrack[3].title
                                    deliveredDescriptionTV.text = arrayListTrack[3].message
                                    deliveredIconIV.setBackgroundResource(R.drawable.green_right_icon)

                                }
                            }
                        }

                    } else {
                        Toast.makeText(
                            this@TrackOrderActivity, trackOrderResponse.message, Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "onResponse_ElseResponse  ${trackOrderResponse.message} ")
                    }
                }
            }

            override fun onFailure(call: Call<TrackOrderResponse>, t: Throwable) {
                Toast.makeText(
                    this@TrackOrderActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "onResponse_FailureResponse${t.message} ")
            }

        })
    }

    private fun cancelOrder() {
        val requestBody: MutableMap<String, String> = HashMap()

        requestBody["reason"] = reason
        requestBody["description"] = descriptionCancel

        val call: Call<CancelOrderResponse> =
            RetrofitClient.instance!!.api.cancelOrder(id = orderId, requestBody)
        call.enqueue(object : Callback<CancelOrderResponse> {
            override fun onResponse(
                call: Call<CancelOrderResponse>, response: Response<CancelOrderResponse>
            ) {
                val cancelResponse = response.body()
                if (cancelResponse!!.status) {

                    Toast.makeText(
                        this@TrackOrderActivity, cancelResponse.message, Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@TrackOrderActivity, DashBoardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                } else {

                    Toast.makeText(
                        this@TrackOrderActivity, cancelResponse.message, Toast.LENGTH_SHORT
                    ).show()
                    Log.d("TAG", "onResponse_ElseResponse  ${cancelResponse.message} ")

                }
            }

            override fun onFailure(call: Call<CancelOrderResponse>, t: Throwable) {
                Toast.makeText(
                    this@TrackOrderActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.d("TAG", "onResponse_FailureResponse${t.message} ")
            }

        })
    }

}