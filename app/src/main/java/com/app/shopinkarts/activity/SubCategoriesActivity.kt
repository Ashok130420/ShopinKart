package com.app.shopinkarts.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.adapter.AccessoriesAdapter
import com.app.shopinkarts.adapter.BottomWearAdapter
import com.app.shopinkarts.adapter.TopWearAdapter
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivitySubCategoriesBinding
import com.app.shopinkarts.response.SubCategoriesResponse
import com.app.shopinkarts.response.SubCategory
import org.json.JSONObject
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
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_categories)

        subCategoryId = intent.extras!!.getString("categoryId", "")
        categoryName = intent.extras!!.getString("categoryName", "")

        binding.headerSubCategories.titleTV.text = categoryName
        binding.headerSubCategories.cartIV.visibility = View.GONE
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
                    val jObjError = JSONObject(response.errorBody()!!.string())
                    Toast.makeText(
                        this@SubCategoriesActivity,
                        jObjError.getString("message"),
                        Toast.LENGTH_LONG
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