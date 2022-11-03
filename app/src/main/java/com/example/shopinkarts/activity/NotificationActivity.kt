package com.example.shopinkarts.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.NotificationAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityNotificationBinding
import com.example.shopinkarts.response.Notification
import com.example.shopinkarts.response.NotificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding
    lateinit var notificationAdapter: NotificationAdapter
    lateinit var sharedPreference: SharedPreference

    var TAG = "NotificationActivity"

    companion object {
        var nInstance: NotificationActivity = NotificationActivity()


        fun getInstance(): NotificationActivity {
            return nInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)

        sharedPreference = SharedPreference(this)

        notificationList()

        binding.headerNotification.nameTV.text = resources.getString(R.string.notifications)
        binding.headerNotification.backIV.setOnClickListener {
            onBackPressed()
        }

    }

    private fun notificationList() {
        val userId = sharedPreference.getUserId()
        val call: Call<NotificationResponse> =
            RetrofitClient.instance!!.api.notification(id = userId)

        call.enqueue(object : Callback<NotificationResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<NotificationResponse>, response: Response<NotificationResponse>
            ) {
                val notificationResponse = response.body()
                if (response.isSuccessful) {
                    if (notificationResponse!!.status) {

                        DashBoardActivity.arrayListNotifications.clear()
                        DashBoardActivity.arrayListNotifications.addAll(notificationResponse.notifications)
                        notificationAdapter =
                            NotificationAdapter(
                                this@NotificationActivity,
                                DashBoardActivity.arrayListNotifications
                            )
                        binding.notificationRV.adapter = notificationAdapter
                        binding.notificationRV.hasFixedSize()
                        notificationAdapter.notifyDataSetChanged()

                        if (DashBoardActivity.arrayListNotifications.isEmpty()) {
                            binding.notificationCL.visibility = View.GONE
                            binding.lottyAnimationCL.visibility = View.VISIBLE

                        } else {
                            binding.notificationCL.visibility = View.VISIBLE
                            binding.lottyAnimationCL.visibility = View.GONE
                        }

//                        Toast.makeText(
//                            this@NotificationActivity,
//                            notificationResponse.message,
//                            Toast.LENGTH_SHORT
//                        ).show()

                        Log.d(TAG, "onFailureResponse: $${notificationResponse.message}")

                    } else {

                        Toast.makeText(
                            this@NotificationActivity,
                            notificationResponse.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "onFailureResponse: $${notificationResponse.message}")
                    }
                }
            }

            override fun onFailure(call: Call<NotificationResponse>, t: Throwable) {
                Log.d(TAG, "onFailureResponse: $${t.message}")
                Toast.makeText(this@NotificationActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}