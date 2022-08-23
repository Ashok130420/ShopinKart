package com.example.shopinkarts.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.databinding.ActivityTermsConditionsBinding

class TermsConditionsActivity : AppCompatActivity() {
    lateinit var binding: ActivityTermsConditionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_terms_conditions)

        binding.headerTermsConditions.nameTV.text = resources.getString(R.string.terms_conditions)
        binding.headerTermsConditions.backIV.setOnClickListener {
            onBackPressed()
        }
    }
}