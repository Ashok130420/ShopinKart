package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityAboutUsBinding


class AboutUsActivity : AppCompatActivity() {

    lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)

        binding.headerAbout.nameTV.text = resources.getString(R.string.customer_support)
        binding.headerAbout.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}