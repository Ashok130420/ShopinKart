package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityPasswordChangedBinding

class PasswordChangedActivity : AppCompatActivity() {

    lateinit var binding: ActivityPasswordChangedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_changed)

        Handler().postDelayed({

            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            finish()

        }, 3000)
    }
}