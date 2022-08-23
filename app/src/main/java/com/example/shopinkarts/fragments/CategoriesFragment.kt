package com.example.shopinkarts.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.AccessoriesAdapter
import com.example.shopinkarts.adapter.BottomWearAdapter
import com.example.shopinkarts.adapter.CategoriesAdapter
import com.example.shopinkarts.adapter.TopWearAdapter
import com.example.shopinkarts.databinding.FragmentCategoriesBinding
import com.example.shopinkarts.model.CategoriesModel


class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoriesAdapter: CategoriesAdapter
    private val imageList = ArrayList<SlideModel>()

    var arrayListCategories: ArrayList<CategoriesModel> = ArrayList()

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

        // adapter for categories item
        categoriesAdapter = CategoriesAdapter(requireContext())
        binding.categoriesRV.adapter = categoriesAdapter
        binding.categoriesRV.isNestedScrollingEnabled = false

        //binding.categoriesHeader.nameTV.text = resources.getString(R.string.explore_categories)


        return binding.root
    }

}