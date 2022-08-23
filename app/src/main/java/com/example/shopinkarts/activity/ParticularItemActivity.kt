package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.ParticularItemAdapter
import com.example.shopinkarts.databinding.ActivityParticularItemBinding

class ParticularItemActivity : AppCompatActivity() {

    lateinit var binding: ActivityParticularItemBinding
    lateinit var particularItemAdapter: ParticularItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_particular_item)

        binding.headerParticularItem.backIV.setOnClickListener {
            onBackPressed()
        }
        // adapter for particular item
        particularItemAdapter = ParticularItemAdapter(this)
        binding.particularItemRV.adapter = particularItemAdapter

        binding.headerParticularItem.cartIV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }

    }
}