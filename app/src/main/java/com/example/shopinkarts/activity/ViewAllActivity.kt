package com.example.shopinkarts.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.NewlyAddedAdapter
import com.example.shopinkarts.databinding.ActivityViewAllBinding

class ViewAllActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewAllBinding
    lateinit var newlyAddedAdapter: NewlyAddedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all)

        // adapter for newly added view all items
        newlyAddedAdapter = NewlyAddedAdapter(this)
        binding.newlyAddedRV.adapter = newlyAddedAdapter

        binding.headerViewAll.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerViewAll.titleTV.text = resources.getString(R.string.newly_added)
        binding.headerViewAll.iconIV.visibility = View.GONE
    }
}