package com.app.shopinkarts.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class TrackOrderActivity : AppCompatActivity() {

    lateinit var binding: ActivityTrackOrderBinding
    var arrayListTrack: ArrayList<TrackOrder> = ArrayList()
    var orderId = ""

    var reasonCancel = ""
    var reason = ""
    var descriptionCancel = ""

    var startDate = ""
    var endDate = ""


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
        cancelOrderTV.visibility = View.VISIBLE
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

            @RequiresApi(Build.VERSION_CODES.O)
            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            override fun onResponse(
                call: Call<TrackOrderResponse>, response: Response<TrackOrderResponse>
            ) {
                val trackOrderResponse = response.body()
                if (response.isSuccessful) {
                    if (trackOrderResponse!!.status) {
                        arrayListTrack.clear()
                        arrayListTrack.addAll(trackOrderResponse.order)

                        val cancelOrderTV = findViewById<TextView>(R.id.cancelOrderTV)
                        for (index in arrayListTrack.indices) {
                            when (index) {
                                0 -> {

                                    binding.orderPlacedTimeTV.text =
                                        " ${arrayListTrack[0].dateTimeStamp}"
                                    binding.orderPlacedTV.text = arrayListTrack[0].title
                                    binding.orderPlacedDescriptionTV.text =
                                        arrayListTrack[0].message
                                    cancelOrderTV.visibility = View.VISIBLE

                                    /*          startDate = arrayListTrack[0].dateTimeStamp
                                              Log.d("startDate", startDate)

                                              var dateInString = startDate
                                              var sdf = SimpleDateFormat("yyyy-MM-dd")
                                              val c: Calendar = Calendar.getInstance()
                                              c.time = sdf.parse(dateInString)
                                              c.add(Calendar.DATE, 5)
                                              sdf = SimpleDateFormat("yyyy-MM-dd")
                                              val resultDate = Date(c.timeInMillis)
                                              dateInString = sdf.format(resultDate)
                                              Log.d("startDate", dateInString)


                                              val date = SimpleDateFormat("yyyy-MM-dd").parse(startDate)
                                              val date1 = SimpleDateFormat("yyyy-MM-dd").parse(dateInString)


                                              val diff: Long = date1!!.time - date!!.time
                                              val seconds = diff / 1000
                                              val minutes = seconds / 60
                                              val hours = minutes / 60
                                              val days = hours / 24
                                              Log.d("startDate", days.toString())

                                              if (days == 5L) {
                                                  val cancelOrderTV =
                                                      findViewById<TextView>(R.id.cancelOrderTV)
                                                  cancelOrderTV.visibility = View.GONE
                                              }*/

                                }
                                1 -> {
                                    val processingTimeTV =
                                        findViewById<TextView>(R.id.processingTimeTV)
                                    val processingTV = findViewById<TextView>(R.id.processingTV)
                                    val processingDescriptionTV =
                                        findViewById<TextView>(R.id.processingDescriptionTV)
                                    val processingIconIV =
                                        findViewById<ImageView>(R.id.processingIconIV)

                                    processingTimeTV.text = " ${arrayListTrack[1].dateTimeStamp}"
                                    processingTV.text = arrayListTrack[1].title
                                    processingDescriptionTV.text = arrayListTrack[1].message
                                    processingIconIV.setBackgroundResource(R.drawable.green_right_icon)

                                    cancelOrderTV.visibility = View.VISIBLE
                                }
                                2 -> {

                                    binding.outForDeliveryDateTV.text =
                                        " ${arrayListTrack[2].dateTimeStamp}"
                                    binding.outForDeliveryTV.text = arrayListTrack[2].title
                                    binding.outForDeliveryDescriptionTV.text =
                                        arrayListTrack[2].message
                                    binding.outForDeliveryIconIV.setBackgroundResource(R.drawable.green_right_icon)
                                    cancelOrderTV.visibility = View.VISIBLE
                                }
                                3 -> {

                                    val deliveredDateTV =
                                        findViewById<TextView>(R.id.deliveredDateTV)
                                    val deliveredTV = findViewById<TextView>(R.id.deliveredTV)
                                    val deliveredDescriptionTV =
                                        findViewById<TextView>(R.id.deliveredDescriptionTV)
                                    val deliveredIconIV =
                                        findViewById<ImageView>(R.id.deliveredIconIV)

                                    deliveredDateTV.text = " ${arrayListTrack[3].dateTimeStamp}"
                                    deliveredTV.text = arrayListTrack[3].title
                                    deliveredDescriptionTV.text = arrayListTrack[3].message
                                    deliveredIconIV.setBackgroundResource(R.drawable.green_right_icon)

                                    startDate = arrayListTrack[3].dateTimeStamp
                                    Log.d("startDate", startDate)

                                    var dateInString = startDate
                                    var sdf = SimpleDateFormat("yyyy-MM-dd")
                                    val c: Calendar = Calendar.getInstance()
                                    c.time = sdf.parse(dateInString)!!
                                    c.add(Calendar.DATE, 5)
                                    sdf = SimpleDateFormat("yyyy-MM-dd")
                                    val resultDate = Date(c.timeInMillis)
                                    dateInString = sdf.format(resultDate)
                                    Log.d("startDate", dateInString)


                                    val date = SimpleDateFormat("yyyy-MM-dd").parse(startDate)
                                    val date1 = SimpleDateFormat("yyyy-MM-dd").parse(dateInString)


                                    val diff: Long = date1!!.time - date!!.time
                                    val seconds = diff / 1000
                                    val minutes = seconds / 60
                                    val hours = minutes / 60
                                    val days = hours / 24
                                    Log.d("startDate", days.toString())

                                    if (days == 5L) {

                                        cancelOrderTV.visibility = View.GONE
                                    }
                                }
                            }
                        }

                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            this@TrackOrderActivity,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
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

    fun printDifference(startDate: Date, endDate: Date) {
        //milliseconds
        var different = endDate.time - startDate.time
        println("startDate : $startDate")
        println("endDate : $endDate")
        println("different : $different")
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        val elapsedDays = different / daysInMilli
        different %= daysInMilli
        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli
        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli
        val elapsedSeconds = different / secondsInMilli
        System.out.printf(
            "%d days, %d hours, %d minutes, %d seconds%n",
            elapsedDays,
            elapsedHours,
            elapsedMinutes,
            elapsedSeconds
        )
        Log.d("startDate", elapsedDays.toString())
    }


}