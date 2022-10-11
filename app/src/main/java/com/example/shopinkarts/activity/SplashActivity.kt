package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.shopinkarts.classes.SharedPreference
import com.onesignal.OneSignal


class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreference: SharedPreference
    var deviceId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.shopinkarts.R.layout.activity_splash)

        deviceId = OneSignal.getDeviceState()?.userId.toString()

        Log.d("TAG_deviceId", "onCreate: $deviceId")

        sharedPreference = SharedPreference(this)

        // Full Screen Hide the status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,

            )
        //hide navigation bar
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_HIDE_NAVIGATION


        Handler().postDelayed({
            if (sharedPreference.isLoginGet()) {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}