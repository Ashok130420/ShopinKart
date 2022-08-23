package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityNewPasswordBinding

class NewPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_password)

        binding.headerNewPassword.nameTV.text = resources.getString(R.string.password_reset)
        binding.headerNewPassword.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.resetTV.setOnClickListener {
            val intent = Intent(this, PasswordChangedActivity::class.java)
            startActivity(intent)
        }
    }
}