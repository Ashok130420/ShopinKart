package com.example.shopinkarts.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivityImageZoomBinding

class ImageZoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityImageZoomBinding
    var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_zoom)

        imageUrl = intent.extras!!.getString("imageUrl", "")

        Glide.with(this).load(imageUrl).into(binding.largeImage)

        binding.back.setOnClickListener {
            onBackPressed()
        }
    }
}