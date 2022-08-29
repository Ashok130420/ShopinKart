package com.example.shopinkarts.fragments

import android.content.Context
import android.content.Intent
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
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ViewAllActivity
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.FragmentHomeBinding
import com.example.shopinkarts.model.ClothShortingModel
import com.example.shopinkarts.model.CommonModel
import com.example.shopinkarts.model.IntroSlide
import com.example.shopinkarts.response.DashBoardResponse
import com.example.shopinkarts.response.ShopFor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var shopForAdapter: ShopForAdapter
    lateinit var newlyAddedAdapter: NewlyAddedAdapter
    lateinit var manufacturerAdapter: ManufacturerAdapter
    lateinit var popularAdapter: PopularAdapter
    lateinit var popularBrandAdapter: PopularBrandAdapter
    lateinit var flashSaleAdapter: FlashSaleAdapter
    lateinit var clothsShortingAdapter: ClothsShortingAdapter
    lateinit var filterItemsAdapter: FilterItemsAdapter
    lateinit var mostPopularAdapter: MostPopularAdapter
    lateinit var topRatedAdapter: TopRatedAdapter
    lateinit var discountForYouAdapter: DiscountForYouAdapter
    lateinit var recommendedAdapter: RecommendedAdapter

    lateinit var commonRecyclerViewAdapter: CommonRecyclerViewAdapter
    private val imageList = ArrayList<SlideModel>()
    private val imageListBanner = ArrayList<SlideModel>()
    private val imageListBanner2 = ArrayList<SlideModel>()
    var arrayList: ArrayList<CommonModel> = ArrayList()
    var arrayListCloths: ArrayList<ClothShortingModel> = ArrayList()

    private lateinit var sharedPreference: SharedPreference
    val arrayListShopFor: ArrayList<ShopFor> = ArrayList()

    private lateinit var introSlideAdapter: IntroSlideAdapter
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500
    val PERIOD_MS: Long = 4000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        sharedPreference = SharedPreference(requireContext())

        setIntroSliderViewPager()
        setupIndicators()
        setCurrentIndicator(0)

        binding.introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        val handler = Handler()
        val update = Runnable {

            binding.introSliderViewPager.setCurrentItem(currentPage % 4, true)
            currentPage++
        }

        timer = Timer()

        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)

        /*    arrayList.clear()
            arrayList.add(CommonModel(view_type = 1, "Shop For", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Preferred Manufacturer", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 2, "Newly Added", "View All", 1, 1))
            arrayList.add(CommonModel(view_type = 2, "Popular", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Popular Brand", "View All", 0, 0))
            commonRecyclerViewAdapter = CommonRecyclerViewAdapter(requireContext(), arrayList)
            binding.recyclerView.adapter = commonRecyclerViewAdapter*/

        /*imageList.clear()
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)*/

        // adapter for shopFor
//        shopForAdapter = ShopForAdapter(requireContext(), arrayList[position].view_type)
//        binding.shopForRV.hasFixedSize()
//        binding.shopForRV.adapter = shopForAdapter

        // adapter for shopFor
        shopForAdapter = ShopForAdapter(requireContext(), arrayListShopFor)
        binding.shopForRV.hasFixedSize()
        binding.shopForRV.adapter = shopForAdapter

        // adapter for newly added
        newlyAddedAdapter = NewlyAddedAdapter(requireContext())
        binding.newlyAddedRV.adapter = newlyAddedAdapter
        binding.newlyAddedRV.isNestedScrollingEnabled = false

        // adapter for Preferred Manufacturer
        manufacturerAdapter = ManufacturerAdapter(requireContext())
        binding.manufacturerRV.adapter = manufacturerAdapter
        binding.manufacturerRV.isNestedScrollingEnabled = false

        // adapter for popular items
        popularAdapter = PopularAdapter(requireContext())
        binding.popularRV.adapter = popularAdapter
        binding.popularRV.isNestedScrollingEnabled = false

        // adapter for popular brand items
        popularBrandAdapter = PopularBrandAdapter(requireContext())
        binding.popularBrandRV.adapter = popularBrandAdapter
        binding.popularBrandRV.isNestedScrollingEnabled = false

        // adapter for flash sale
        flashSaleAdapter = FlashSaleAdapter(requireContext())
        binding.flashSaleRV.adapter = flashSaleAdapter
        binding.flashSaleRV.isNestedScrollingEnabled = false

        arrayListCloths.clear()
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "T-Shirts"
            )
        )
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "Shirts"
            )
        )
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "Shorts"
            )
        )
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "Jeans"
            )
        )
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "Trousers"
            )
        )
        arrayListCloths.add(
            ClothShortingModel(
                cloths = "Accessories"
            )
        )
        // adapter for shorting cloths
        clothsShortingAdapter = ClothsShortingAdapter(requireContext(), arrayListCloths)
        binding.clothsShortingRV.adapter = clothsShortingAdapter
        binding.clothsShortingRV.isNestedScrollingEnabled = false

        // adapter for filter items
        filterItemsAdapter = FilterItemsAdapter(requireContext())
        binding.filterItemsRV.adapter = filterItemsAdapter
        binding.filterItemsRV.isNestedScrollingEnabled = false

        // adapter for most popular items
        mostPopularAdapter = MostPopularAdapter(requireContext())
        binding.mostPopularRV.adapter = mostPopularAdapter
        binding.mostPopularRV.isNestedScrollingEnabled = false

        // adapter for top rated items
        topRatedAdapter = TopRatedAdapter(requireContext())
        binding.topRatedRV.adapter = topRatedAdapter
        binding.topRatedRV.isNestedScrollingEnabled = false

        binding.newlyAddedAllTV.setOnClickListener {
            val intent = Intent(context, ViewAllActivity::class.java)
            startActivity(intent)
        }
        binding.mostPopularAllTV.setOnClickListener {
            val intent = Intent(context, ViewAllActivity::class.java)
            startActivity(intent)
        }


        imageListBanner.clear()
        imageListBanner.add(SlideModel(R.drawable.banner_2))
        imageListBanner.add(SlideModel(R.drawable.banner_1))
        imageListBanner.add(SlideModel(R.drawable.banner_2))
        imageListBanner.add(SlideModel(R.drawable.banner_1))
        binding.imageSliderBanner.setImageList(imageListBanner, ScaleTypes.FIT)

        imageListBanner2.clear()
        imageListBanner2.add(SlideModel(R.drawable.shopping))
        imageListBanner2.add(SlideModel(R.drawable.shopping))
        imageListBanner2.add(SlideModel(R.drawable.shopping))
        imageListBanner2.add(SlideModel(R.drawable.shopping))
        imageListBanner2.add(SlideModel(R.drawable.shopping))
        binding.imageSliderBanner2.setImageList(imageListBanner2, ScaleTypes.FIT)

        // adapter for discount for you items
        discountForYouAdapter = DiscountForYouAdapter(requireContext())
        binding.discountForYouRV.adapter = discountForYouAdapter
        binding.discountForYouRV.isNestedScrollingEnabled = false

        // adapter for recommended items
        recommendedAdapter = RecommendedAdapter(requireContext())
        binding.recommendedRV.adapter = recommendedAdapter
        binding.recommendedRV.isNestedScrollingEnabled = false


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dashBoardList()
    }

    // setup Indicators
    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(introSlideAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT)
        layoutParms.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(context)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(context, R.drawable.indicator_inactive)
                )
                this?.layoutParams = layoutParms
            }

            binding.indicatorsContainers.addView(indicators[i])
        }
    }

    //Current Indicator
    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorsContainers.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainers[i] as ImageView
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

    private fun setIntroSliderViewPager() {
        introSlideAdapter = IntroSlideAdapter(
            listOf(
                IntroSlide(
                    R.drawable.banner_2
                ), IntroSlide(
                    R.drawable.banner_1
                ), IntroSlide(
                    R.drawable.banner_2
                ), IntroSlide(
                    R.drawable.banner_1
                )
            )
        )
        binding.introSliderViewPager.adapter = introSlideAdapter
    }

    private fun dashBoardList() {

        val userId = sharedPreference.getUserId()
        val call: Call<DashBoardResponse> =
            RetrofitClient.instance!!.api.dashBoard(userId!!)
        call.enqueue(object : Callback<DashBoardResponse> {
            override fun onResponse(
                call: Call<DashBoardResponse>,
                response: Response<DashBoardResponse>
            ) {
                val dashBoardResponse = response.body()
                if (response.isSuccessful) {

                    if (dashBoardResponse!!.status) {
                        arrayListShopFor.clear()
                        arrayListShopFor.addAll(dashBoardResponse.shopFor)
                        shopForAdapter = ShopForAdapter(requireContext(), arrayListShopFor)
                        binding.shopForRV.adapter = shopForAdapter
                        shopForAdapter.notifyDataSetChanged()

                    }
                    Log.d("TAG", "onResponse_SuccessResponse: ${dashBoardResponse.message}")
                } else {
                    Toast.makeText(
                        requireContext(),
                        "${dashBoardResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            override fun onFailure(call: Call<DashBoardResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: $${t.message}")
                Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }

}