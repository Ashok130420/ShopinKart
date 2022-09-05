package com.example.shopinkarts.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.CategoriesAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.FragmentCategoriesBinding
import com.example.shopinkarts.response.CategoriesResponse
import com.example.shopinkarts.response.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoriesAdapter: CategoriesAdapter
    private val imageList = ArrayList<SlideModel>()
    var arrayListCategories: ArrayList<Category> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)

        imageList.clear()
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        categoriesList()
    }

    private fun categoriesList() {

        val call: Call<CategoriesResponse> = RetrofitClient.instance!!.api.categories()
        call.enqueue(object : Callback<CategoriesResponse> {
            override fun onResponse(
                call: Call<CategoriesResponse>,
                response: Response<CategoriesResponse>
            ) {
                val categoriesResponse = response.body()
                if (response.isSuccessful) {
                    if (categoriesResponse!!.status) {

                        arrayListCategories.clear()
                        arrayListCategories.addAll(categoriesResponse.categories)
                        categoriesAdapter = CategoriesAdapter(requireContext(), arrayListCategories)
                        binding.categoriesRV.adapter = categoriesAdapter
                        categoriesAdapter.notifyDataSetChanged()
                    }
                    Log.d("TAG", "onResponse_SuccessResponse :${categoriesResponse.message}")
                } else {
                    Toast.makeText(
                        requireContext(),
                        "${categoriesResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<CategoriesResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: ${t.message}")
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

}