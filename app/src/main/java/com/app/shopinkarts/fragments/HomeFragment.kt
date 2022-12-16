package com.app.shopinkarts.fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import androidx.viewpager2.widget.ViewPager2
import com.app.shopinkarts.R
import com.app.shopinkarts.activity.SearchActivity
import com.app.shopinkarts.activity.ViewAllActivity
import com.app.shopinkarts.adapter.*
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.CustomScrollView
import com.app.shopinkarts.classes.CustomScrollView.OnBottomReachedListener
import com.app.shopinkarts.databinding.FragmentHomeBinding
import com.app.shopinkarts.model.*
import com.app.shopinkarts.response.*
import com.facebook.shimmer.ShimmerFrameLayout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var shopForAdapter: ShopForAdapter
    lateinit var newlyAddedAdapter: NewlyAddedAdapter
    lateinit var manufacturerAdapter: ManufacturerAdapter
    lateinit var dealOfTheDayAdapter: DealOfTheDayAdapter
    lateinit var popularBrandAdapter: PopularBrandAdapter
    lateinit var flashSaleAdapter: FlashSaleAdapter
    lateinit var clothsShortingAdapter: ClothsShortingAdapter
    lateinit var filterItemsAdapter: FilterItemsAdapter
    lateinit var mostPopularAdapter: MostPopularAdapter
    lateinit var topRatedAdapter: TopRatedAdapter
    lateinit var discountForYouAdapter: DiscountForYouAdapter
    lateinit var recommendedAdapter: RecommendedAdapter
    lateinit var endlessProductsAdapter: EndlessProductsAdapter
    lateinit var banner2Adapter: Banner2Adapter
    lateinit var banner3Adapter: Banner3Adapter
    lateinit var shimmerHome: ShimmerFrameLayout


    lateinit var commonRecyclerViewAdapter: CommonRecyclerViewAdapter

    var arrayList: ArrayList<CommonModel> = ArrayList()
    var arrayListCloths: ArrayList<ClothShortingModel> = ArrayList()

    val arraylistBanner1: ArrayList<Banner> = ArrayList()
    val arraylistBanner2: ArrayList<Banner> = ArrayList()
    val arraylistBanner3: ArrayList<Banner> = ArrayList()

    private lateinit var banner1Adapter: Banner1Adapter
    var currentPage = 0
    var currentPage2 = 0
    var currentPage3 = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 4000

    val arrayListEndLessProduct: ArrayList<Product> = ArrayList()

    var page = 0
    var isLoading = false
    var isLastPage: Boolean = false
    val limit = 8
    lateinit var layoutManager: LinearLayoutManager

    companion object {
        var mInstance: HomeFragment = HomeFragment()

        val arrayListNewlyAdded: ArrayList<NewlyAdded> = ArrayList()
        val arrayListMostPopular: ArrayList<MostPopular> = ArrayList()
        val arrayListTopRated: ArrayList<TopRated> = ArrayList()
        val arrayListFlashSale: ArrayList<FlashSale> = ArrayList()
        val arrayListDealOfDay: ArrayList<DealOfDay> = ArrayList()
        val arrayListShopFor: ArrayList<ShopFor> = ArrayList()
        val arrayListDiscountForYou: ArrayList<DiscountForYou> = ArrayList()
        val arrayListRecommended: ArrayList<RecommendedItem> = ArrayList()
        val arrayListPreferredManufacturer: ArrayList<PreferredManufacturer> = ArrayList()
        val arrayListPopularBrand: ArrayList<PreferredManufacturer> = ArrayList()

        var listItems = 0

        fun getInstance(): HomeFragment {
            return mInstance
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        //activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


        endlessProductsAdapter = EndlessProductsAdapter(requireContext(), arrayListEndLessProduct)
        binding.allProductsRV.adapter = endlessProductsAdapter
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.allProductsRV.layoutManager = layoutManager
        binding.allProductsRV.hasFixedSize()
        binding.allProductsRV.isNestedScrollingEnabled = false
//        binding.allProductsRV.maxFlingVelocity


        /* binding.scrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
             val x = scrollY - oldScrollY
             if (x > 0) {
                 Log.d("TAG", "loadMoreItems: safsafasf..........")
                 isLoading = true
                 binding.progressbar.visibility = View.VISIBLE
                 endLessProductList()
             } else if (x < 0) {

             }
         }*/

        val scrollView = binding.scrollView

        scrollView.onBottomReachedListener = object : OnBottomReachedListener {
            override fun onBottomReached() {
                // ScrollView Reached bottom
                if (!isLoading) {
                    Log.d("TAG", "loadMoreItems: safsafasf..........")
                    isLoading = true
                    binding.progressbar.visibility = View.VISIBLE
                    endLessProductList()
                }
            }
        }
        

        dashBoardList()
        page = 0
        arrayListEndLessProduct.clear()
        //endLessProductList()
        shimmerHome = binding.shimmerViewBanner


        binding.pullToRefresh.setOnRefreshListener(OnRefreshListener {
//            onResume()

//            dashBoardList()
//            page = 0
//            arrayListEndLessProduct.clear()
//            endLessProductList()

            binding.pullToRefresh.isRefreshing = false
        })

//      banner 1st fun
        setCurrentIndicator(0)

//      banner 2nd fun
        setCurrentIndicatorBanner2(0)

//      banner 3rd fun
        //setCurrentIndicatorBanner3(0)

//      disable scrolling
        // binding.scrollView.isEnableScrolling = false

//        banner 1st
        binding.introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

//        banner 2nd
        binding.banner2ViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicatorBanner2(position)
            }
        })

//        banner 3rd
        /*  binding.banner3ViewPager.registerOnPageChangeCallback(object :
              ViewPager2.OnPageChangeCallback() {
              override fun onPageSelected(position: Int) {
                  super.onPageSelected(position)
                  setCurrentIndicatorBanner3(position)
              }
          })*/

//        arrayList.clear()
//        arrayList.add(CommonModel(view_type = 1, "Shop For", "View All", 0, 0))
//        arrayList.add(CommonModel(view_type = 3, "Preferred Manufacturer", "View All", 0, 0))
//        arrayList.add(CommonModel(view_type = 2, "Newly Added", "View All", 1, 1))
//        arrayList.add(CommonModel(view_type = 2, "Popular", "View All", 0, 0))
//        arrayList.add(CommonModel(view_type = 3, "Popular Brand", "View All", 0, 0))
//        commonRecyclerViewAdapter = CommonRecyclerViewAdapter(requireContext(), arrayList)
//        binding.recyclerView.adapter = commonRecyclerViewAdapter


//        adapter for shopFor
//        shopForAdapter = ShopForAdapter(requireContext(), arrayList[position].view_type)
//        binding.shopForRV.hasFixedSize()
//        binding.shopForRV.adapter = shopForAdapter

        binding.preferredManufacturerAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "preferredManufacturer")
            startActivity(intent)
        }

        binding.mostPopularAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "mostPopular")
            startActivity(intent)
        }

        binding.topRatedAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "topRated")
            startActivity(intent)
        }

        binding.newlyAddedAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "newlyAdded")
            startActivity(intent)
        }

        binding.flashSaleAllItemsCL.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "flashSale")
            startActivity(intent)
        }

        binding.dealOfDayAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "dealOfDay")
            startActivity(intent)
        }

        binding.popularBrandAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "popularBrand")
            startActivity(intent)
        }

        binding.discountForYouAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "discountForYou")
            startActivity(intent)
        }

        binding.recommendedAllTV.setOnClickListener {
            listItems = 1
            val intent = Intent(requireContext(), ViewAllActivity::class.java)
            intent.putExtra("from", "recommended")
            startActivity(intent)
        }

        binding.searchTV.setOnClickListener {
            val intent = Intent(requireContext(), SearchActivity::class.java)
            startActivity(intent)
        }


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
        /*  clothsShortingAdapter = ClothsShortingAdapter(requireContext(), arrayListCloths)
          binding.clothsShortingRV.adapter = clothsShortingAdapter
          binding.clothsShortingRV.isNestedScrollingEnabled = false

          // adapter for filter items
          filterItemsAdapter = FilterItemsAdapter(requireContext())
          binding.filterItemsRV.adapter = filterItemsAdapter
          binding.filterItemsRV.isNestedScrollingEnabled = false*/

        val time = object : CountDownTimer(20000000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var diff: Long = millisUntilFinished
                val secondsInMilli: Long = 1000
                val minutesInMilli = secondsInMilli * 60
                val hoursInMilli = minutesInMilli * 60
                val daysInMilli = hoursInMilli * 24

                val elapsedDays = diff / daysInMilli
                diff %= daysInMilli

                val elapsedHours = diff / hoursInMilli
                diff %= hoursInMilli

                val elapsedMinutes = diff / minutesInMilli
                diff %= minutesInMilli

                val elapsedSeconds = diff / secondsInMilli

                binding.clock1TV.text = "$elapsedHours : $elapsedMinutes : $elapsedSeconds "
//                    "$elapsedDays days $elapsedHours hs $elapsedMinutes min $elapsedSeconds sec"
            }

            override fun onFinish() {

                binding.clock1TV.text = " 00 : 00 : 00 "

            }
        }
        time.start()
        return binding.root
    }

    private fun dashBoardList() {

        val call: Call<DashBoardResponse> = RetrofitClient.instance!!.api.dashBoard()
        call.enqueue(object : Callback<DashBoardResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<DashBoardResponse>, response: Response<DashBoardResponse>
            ) {
                binding.searchTV.visibility = View.VISIBLE
                binding.searchIV.visibility = View.VISIBLE
                binding.shopForTV.visibility = View.VISIBLE
                binding.preferredManufacturerTV.visibility = View.VISIBLE
//                binding.preferredManufacturerAllTV.visibility = View.VISIBLE
                binding.mostPopularTV.visibility = View.VISIBLE
                binding.mostPopularAllTV.visibility = View.VISIBLE
                binding.topRatedTV.visibility = View.VISIBLE
                binding.topRatedAllTV.visibility = View.VISIBLE

                shimmerHome.stopShimmer()
                shimmerHome.visibility = View.GONE

                val dashBoardResponse = response.body()
                if (response.isSuccessful && context != null) {

                    if (dashBoardResponse!!.status) {

//                      scrolling enable
                        // binding.scrollView.isEnableScrolling = true

                        arrayListShopFor.clear()
                        arrayListShopFor.addAll(dashBoardResponse.shopFor)
                        shopForAdapter = ShopForAdapter(requireContext(), arrayListShopFor)
                        binding.shopForRV.adapter = shopForAdapter
                        binding.shopForRV.hasFixedSize()
                        binding.shopForRV.isNestedScrollingEnabled = false
                        shopForAdapter.notifyDataSetChanged()

                        arrayListPreferredManufacturer.clear()
                        arrayListPreferredManufacturer.addAll(dashBoardResponse.preferredManufacturers)
                        manufacturerAdapter =
                            ManufacturerAdapter(requireContext(), arrayListPreferredManufacturer)
                        binding.manufacturerRV.adapter = manufacturerAdapter
                        binding.manufacturerRV.isNestedScrollingEnabled = false
                        manufacturerAdapter.notifyDataSetChanged()

                        arrayListPopularBrand.clear()
                        arrayListPopularBrand.addAll(dashBoardResponse.preferredManufacturers)
                        popularBrandAdapter = PopularBrandAdapter(
                            requireContext(), arrayListPopularBrand
                        )
                        binding.popularBrandRV.adapter = popularBrandAdapter
                        binding.popularBrandRV.isNestedScrollingEnabled = false
                        popularBrandAdapter.notifyDataSetChanged()

                        arrayListNewlyAdded.clear()
                        arrayListNewlyAdded.addAll(dashBoardResponse.newlyAdded)
                        newlyAddedAdapter = NewlyAddedAdapter(requireContext(), arrayListNewlyAdded)
                        binding.newlyAddedRV.adapter = newlyAddedAdapter
                        binding.newlyAddedRV.isNestedScrollingEnabled = false
                        newlyAddedAdapter.notifyDataSetChanged()

                        arraylistBanner1.clear()
                        arraylistBanner1.addAll(dashBoardResponse.banners)
                        banner1Adapter = Banner1Adapter(requireContext(), arraylistBanner1)
                        binding.introSliderViewPager.adapter = banner1Adapter
//                        binding.dotsIndicator.attachTo(binding.introSliderViewPager)
                        setupIndicators()
                        autoSlideBanner1(arraylistBanner1.size)

                        arraylistBanner2.clear()
                        arraylistBanner2.addAll(dashBoardResponse.banners)
                        banner2Adapter = Banner2Adapter(requireContext(), arraylistBanner2)
                        binding.banner2ViewPager.adapter = banner2Adapter
//                        binding.dotsIndicatorBanner2.attachTo(binding.banner2ViewPager)
                        setupIndicatorsBanner2()
                        autoSlideBanner2(arraylistBanner2.size)

                        arraylistBanner3.clear()
                        arraylistBanner3.addAll(dashBoardResponse.banners)
                        banner3Adapter = Banner3Adapter(requireContext(), arraylistBanner3)
                        //binding.banner3ViewPager.adapter = banner3Adapter
//                        binding.dotsIndicatorBanner2.attachTo(binding.banner2ViewPager)
                        setupIndicatorsBanner3()
                        //autoSlideBanner3(arraylistBanner3.size)

                        arrayListMostPopular.clear()
                        arrayListMostPopular.addAll(dashBoardResponse.mostPopular)
                        mostPopularAdapter =
                            MostPopularAdapter(requireContext(), arrayListMostPopular)
                        binding.mostPopularRV.adapter = mostPopularAdapter
                        binding.mostPopularRV.isNestedScrollingEnabled = false
                        mostPopularAdapter.notifyDataSetChanged()

                        arrayListTopRated.clear()
                        arrayListTopRated.addAll(dashBoardResponse.topRated)
                        topRatedAdapter = TopRatedAdapter(requireContext(), arrayListTopRated)
                        binding.topRatedRV.adapter = topRatedAdapter
                        binding.topRatedRV.isNestedScrollingEnabled = false
                        topRatedAdapter.notifyDataSetChanged()

//                        arrayListFlashSale.clear()
//                        arrayListFlashSale.addAll(dashBoardResponse.flashSale)
//                        flashSaleAdapter = FlashSaleAdapter(requireContext(), arrayListFlashSale)
//                        binding.flashSaleRV.adapter = flashSaleAdapter
//                        binding.flashSaleRV.isNestedScrollingEnabled = false
//                        flashSaleAdapter.notifyDataSetChanged()

//                        arrayListDealOfDay.clear()
//                        arrayListDealOfDay.addAll(dashBoardResponse.dealOfDay)
//                        dealOfTheDayAdapter =
//                            DealOfTheDayAdapter(requireContext(), arrayListDealOfDay)
//                        binding.dealOfDayRV.adapter = dealOfTheDayAdapter
//                        binding.dealOfDayRV.isNestedScrollingEnabled = false
//                        dealOfTheDayAdapter.notifyDataSetChanged()

                        arrayListDiscountForYou.clear()
                        arrayListDiscountForYou.addAll(dashBoardResponse.discountForYou)
                        discountForYouAdapter =
                            DiscountForYouAdapter(requireContext(), arrayListDiscountForYou)
                        binding.discountForYouRV.adapter = discountForYouAdapter
                        binding.discountForYouRV.isNestedScrollingEnabled = false
                        discountForYouAdapter.notifyDataSetChanged()

//                        arrayListRecommended.clear()
//                        arrayListRecommended.addAll(dashBoardResponse.recommendedItems)
//                        recommendedAdapter =
//                            RecommendedAdapter(requireContext(), arrayListRecommended)
//                        binding.recommendedRV.adapter = recommendedAdapter
//                        binding.recommendedRV.isNestedScrollingEnabled = false
//                        recommendedAdapter.notifyDataSetChanged()


                    }

                    Log.d("TAG", "onResponse_SuccessResponse: ${dashBoardResponse.message}")
                } else {
                    if (context != null) {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            requireContext(), jObjError.getString("message"), Toast.LENGTH_LONG
                        ).show()
//                        Log.d("TAG", "onResponse_FailedResponse: $jObjError.getString(message)")
                    }
                }
            }

            override fun onFailure(call: Call<DashBoardResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: $${t.message}")
                if (context != null) Toast.makeText(
                    requireContext(), "${t.message}", Toast.LENGTH_SHORT
                ).show()
            }

        })

    }

    private fun endLessProductList() {
        val call: Call<EndlessProductsResponse> =
            RetrofitClient.instance!!.api.endLessProduct(skip = page, limit = limit)
        call.enqueue(object : Callback<EndlessProductsResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<EndlessProductsResponse>, response: Response<EndlessProductsResponse>
            ) {
                val endLessResponse = response.body()
                if (response.isSuccessful && context != null) {
                    if (endLessResponse!!.status) {


//                        val start = ((page) * limit) + 1
//                        val end = (page + 1) * limit

                        //arrayListEndLessProduct.clear()
                        arrayListEndLessProduct.addAll(endLessResponse.products)
//                        for (i: Int in start..end) {
//                            arrayListEndLessProduct.addAll(endLessResponse.products)
//                        }


                        Log.d("TAG", "onResponse: ${arrayListEndLessProduct.size}")
                        endlessProductsAdapter = EndlessProductsAdapter(
                            requireContext(), arrayListEndLessProduct
                        )
                        binding.allProductsRV.adapter = endlessProductsAdapter
                        endlessProductsAdapter.notifyDataSetChanged()
                        isLoading = false
                        binding.progressbar.visibility = View.GONE
                        if (!isLoading && endLessResponse.products.isNotEmpty()) page = page + limit
                    }
                }
            }

            override fun onFailure(call: Call<EndlessProductsResponse>, t: Throwable) {

            }

        })

    }

    fun autoSlideBanner1(size: Int) {
        val handler = Handler()
        val update = Runnable {
            binding.introSliderViewPager.setCurrentItem(currentPage % size, true)
            currentPage++
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    fun autoSlideBanner2(size: Int) {
        val handler = Handler()
        val update = Runnable {
            binding.banner2ViewPager.setCurrentItem(currentPage2 % size, true)
            currentPage2++
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    /*  fun autoSlideBanner3(size: Int) {
          val handler = Handler()
          val update = Runnable {
              binding.banner3ViewPager.setCurrentItem(
                  currentPage3 % size, true
              )
              currentPage3++
          }

          timer = Timer()
          timer!!.schedule(
              object : TimerTask() {
                  override fun run() {
                      handler.post(update)
                  }
              }, DELAY_MS, PERIOD_MS
          )

      }*/

    // banner 1
    @SuppressLint("NotifyDataSetChanged")
    private fun setupIndicators() {

        banner1Adapter = Banner1Adapter(requireContext(), arraylistBanner1)
        binding.introSliderViewPager.adapter = banner1Adapter
        banner1Adapter.notifyDataSetChanged()

        val indicators = arrayOfNulls<ImageView>(banner1Adapter.arrayList.size)
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

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorsContainers.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainers[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.indicator_active
                    )
                })
            } else {
                imageView.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.indicator_inactive
                    )
                })
            }
        }
    }

    //  banner 2
    private fun setupIndicatorsBanner2() {
        val indicators = arrayOfNulls<ImageView>(banner2Adapter.itemCount)
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
            binding.indicatorsBanner2.addView(indicators[i])
        }
    }

    private fun setCurrentIndicatorBanner2(index: Int) {

        val childCount = binding.indicatorsBanner2.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsBanner2[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.indicator_active
                    )
                })
            } else {
                imageView.setImageDrawable(context?.let {
                    ContextCompat.getDrawable(
                        it, R.drawable.indicator_inactive
                    )
                })

            }
        }

    }

    // banner 3
    private fun setupIndicatorsBanner3() {
        val indicators = arrayOfNulls<ImageView>(banner3Adapter.itemCount)
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
//            binding.indicatorsBanner3.addView(indicators[i])
        }
    }

//    private fun setCurrentIndicatorBanner3(index: Int) {
//
//        val childCount = binding.indicatorsBanner3.childCount
//        for (i in 0 until childCount) {
//            val imageView = binding.indicatorsBanner3[i] as ImageView
//            if (i == index) {
//                imageView.setImageDrawable(context?.let {
//                    ContextCompat.getDrawable(
//                        it, R.drawable.indicator_active
//                    )
//                })
//            } else {
//                imageView.setImageDrawable(context?.let {
//                    ContextCompat.getDrawable(
//                        it, R.drawable.indicator_inactive
//                    )
//                })
//
//            }
//        }
//
//    }

}


