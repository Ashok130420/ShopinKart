package com.app.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityPasswordChangedBinding

class PasswordChangedActivity : AppCompatActivity() {

    lateinit var binding: ActivityPasswordChangedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_password_changed)

        Handler().postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, 2000)
    }
}