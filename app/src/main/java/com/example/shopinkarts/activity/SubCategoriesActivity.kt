package com.example.shopinkarts.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.AccessoriesAdapter
import com.example.shopinkarts.adapter.BottomWearAdapter
import com.example.shopinkarts.adapter.TopWearAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.Utils
import com.example.shopinkarts.databinding.ActivitySubCategoriesBinding
import com.example.shopinkarts.response.SubCategoriesResponse
import com.example.shopinkarts.response.SubCategory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubCategoriesActivity : AppCompatActivity() {

    lateinit var binding: ActivitySubCategoriesBinding
    lateinit var topWearAdapter: TopWearAdapter
    lateinit var bottomWearAdapter: BottomWearAdapter
    lateinit var accessoriesAdapter: AccessoriesAdapter

    var arrayListSubCategories: ArrayList<SubCategory> = ArrayList()
    var subCategoryId = ""
    var categoryName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_categories)

        subCategoryId = intent.extras!!.getString("categoryId", "")
        categoryName = intent.extras!!.getString("categoryName", "")

        binding.headerSubCategories.titleTV.text = categoryName

        binding.headerSubCategories.backIV.setOnClickListener {
          finish()
        }

        subCategoriesList()

        bottomWearAdapter = BottomWearAdapter(this)

        // adapter for accessories
        accessoriesAdapter = AccessoriesAdapter(this)

    }

    private fun subCategoriesList() {

        val call: Call<SubCategoriesResponse> =
            RetrofitClient.instance!!.api.subCategories(subCategoryId /*, bannerSubCategoryId*/)
        call.enqueue(object : Callback<SubCategoriesResponse> {
            override fun onResponse(
                call: Call<SubCategoriesResponse>,
                response: Response<SubCategoriesResponse>
            ) {
                val subCategoriesResponse = response.body()
                if (response.isSuccessful) {
                    if (subCategoriesResponse!!.status) {

                        arrayListSubCategories.clear()
                        arrayListSubCategories.addAll(subCategoriesResponse.subCategories)
                        topWearAdapter =
                            TopWearAdapter(this@SubCategoriesActivity, arrayListSubCategories)
                        binding.topWearRV.adapter = topWearAdapter
                        topWearAdapter.notifyDataSetChanged()
                    }
                    Log.d("TAG", "onResponse_SuccessResponse ${subCategoriesResponse.message} ")
                } else {
                    Toast.makeText(
                        this@SubCategoriesActivity,
                        "${subCategoriesResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<SubCategoriesResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: ${t.message}")
                Toast.makeText(this@SubCategoriesActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}