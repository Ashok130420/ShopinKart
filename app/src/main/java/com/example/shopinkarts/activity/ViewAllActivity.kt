package com.example.shopinkarts.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.FilterItemsAdapter
import com.example.shopinkarts.adapter.NewlyAddedAdapter
import com.example.shopinkarts.databinding.ActivityViewAllBinding
import com.example.shopinkarts.model.CartModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ViewAllActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewAllBinding
    lateinit var filterItemsAdapter: FilterItemsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all)

        // adapter for view all items

        filterItemsAdapter = FilterItemsAdapter(this)
        binding.viewAllRV.adapter = filterItemsAdapter

        binding.headerViewAll.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerViewAll.titleTV.text = resources.getString(R.string.newly_added)
        binding.headerViewAll.iconIV.visibility = View.GONE
    }

}