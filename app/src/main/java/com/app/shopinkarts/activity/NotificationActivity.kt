package com.app.shopinkarts.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.adapter.NotificationAdapter
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityNotificationBinding
import com.app.shopinkarts.response.NotificationResponse
import org.json.JSONObject
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


                        Log.d(TAG, "onFailureResponse: $${notificationResponse.message}")

                    } else {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            this@NotificationActivity,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
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