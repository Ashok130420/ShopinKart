package com.example.shopinkarts.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.AccessoriesAdapter
import com.example.shopinkarts.adapter.BottomWearAdapter
import com.example.shopinkarts.adapter.TopWearAdapter
import com.example.shopinkarts.databinding.ActivitySubCategoriesBinding

class SubCategoriesActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubCategoriesBinding
    lateinit var topWearAdapter: TopWearAdapter
    lateinit var bottomWearAdapter: BottomWearAdapter
    lateinit var accessoriesAdapter: AccessoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_categories)
        binding.headerSubCategories.titleTV.text = resources.getString(R.string.men)
        binding.headerSubCategories.backIV.setOnClickListener {
            onBackPressed()
        }

        // adapter for top wear categories
        topWearAdapter = TopWearAdapter(this)
        binding.topWearRV.adapter = topWearAdapter
        binding.topWearRV.isNestedScrollingEnabled = false

        // adapter for bottom wear categories
        bottomWearAdapter = BottomWearAdapter(this)
        binding.bottomWearRV.adapter = bottomWearAdapter
        binding.bottomWearRV.isNestedScrollingEnabled = false

        // adapter for accessories
        accessoriesAdapter = AccessoriesAdapter(this)
        binding.accessoriesRV.adapter = accessoriesAdapter
        binding.accessoriesRV.isNestedScrollingEnabled = false


    }
}