package com.example.shopinkarts.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.CategoriesAdapter
import com.example.shopinkarts.adapter.CategoryBannerAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.FragmentCategoriesBinding
import com.example.shopinkarts.model.CategoryBannerSlide
import com.example.shopinkarts.response.CategoriesResponse
import com.example.shopinkarts.response.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CategoriesFragment : Fragment() {

    lateinit var binding: FragmentCategoriesBinding
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoryBannerAdapter: CategoryBannerAdapter
    var arrayListCategories: ArrayList<Category> = ArrayList()
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 3000
    lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)

        mLayoutManager = LinearLayoutManager(requireContext())
        setCategoryBannerViewPager()
        setupIndicatorsCategoryBanner()
        setCurrentIndicatorCategoryBanner(0)

        val handler = Handler()
        val update = Runnable {
            binding.categoryBannerViewPager.setCurrentItem(
                currentPage % categoryBannerAdapter.itemCount,
                true
            )
            currentPage++
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)


        binding.categoryBannerViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicatorCategoryBanner(position)
            }
        })
        categoriesList()
        return binding.root
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
                        if (context != null) {
                            categoriesAdapter =
                                CategoriesAdapter(requireContext(), arrayListCategories)
                            binding.categoriesRV.adapter = categoriesAdapter
                            binding.categoriesRV.isNestedScrollingEnabled = false
                            categoriesAdapter.notifyDataSetChanged()
                        }

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

    // banner 3
    private fun setupIndicatorsCategoryBanner() {
        val indicators = arrayOfNulls<ImageView>(categoryBannerAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT)
        layoutParms.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(requireContext())
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(requireContext(), R.drawable.indicator_inactive)
                )
                this?.layoutParams = layoutParms
            }
            binding.indicatorsCategoryBanner.addView(indicators[i])
        }
    }

    private fun setCurrentIndicatorCategoryBanner(index: Int) {

        val childCount = binding.indicatorsCategoryBanner.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsCategoryBanner[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    context?.let { ContextCompat.getDrawable(it, R.drawable.indicator_active) }
                )
            } else {
                imageView.setImageDrawable(
                    context?.let { ContextCompat.getDrawable(it, R.drawable.indicator_inactive) }
                )

            }
        }

    }

    private fun setCategoryBannerViewPager() {
        categoryBannerAdapter = CategoryBannerAdapter(
            listOf(
                CategoryBannerSlide(R.drawable.banner_1),
                CategoryBannerSlide(R.drawable.banner_2),
                CategoryBannerSlide(R.drawable.banner_1),
                CategoryBannerSlide(R.drawable.banner_2)
            )
        )
        binding.categoryBannerViewPager.adapter = categoryBannerAdapter
    }

}
