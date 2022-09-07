package com.example.shopinkarts.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ViewAllActivity
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.FragmentHomeBinding
import com.example.shopinkarts.model.Banner2Slide
import com.example.shopinkarts.model.Banner3Slide
import com.example.shopinkarts.model.ClothShortingModel
import com.example.shopinkarts.model.CommonModel
import com.example.shopinkarts.response.*
import com.facebook.shimmer.ShimmerFrameLayout
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
    lateinit var dealOfTheDayAdapter: DealOfTheDayAdapter
    lateinit var popularBrandAdapter: PopularBrandAdapter
    lateinit var flashSaleAdapter: FlashSaleAdapter
    lateinit var clothsShortingAdapter: ClothsShortingAdapter
    lateinit var filterItemsAdapter: FilterItemsAdapter
    lateinit var mostPopularAdapter: MostPopularAdapter
    lateinit var topRatedAdapter: TopRatedAdapter
    lateinit var discountForYouAdapter: DiscountForYouAdapter
    lateinit var recommendedAdapter: RecommendedAdapter
    lateinit var banner2Adapter: Banner2Adapter
    lateinit var banner3Adapter: Banner3Adapter
    lateinit var shimmerShopFor: ShimmerFrameLayout
    lateinit var shimmerManufacturer: ShimmerFrameLayout
    lateinit var shimmerMostPopular: ShimmerFrameLayout
    lateinit var shimmerHome: ShimmerFrameLayout

    lateinit var commonRecyclerViewAdapter: CommonRecyclerViewAdapter
    private val imageListBanner = ArrayList<SlideModel>()
    private val imageListBanner2 = ArrayList<SlideModel>()
    var arrayList: ArrayList<CommonModel> = ArrayList()
    var arrayListCloths: ArrayList<ClothShortingModel> = ArrayList()

    val arrayListShopFor: ArrayList<ShopFor> = ArrayList()
    val arrayListPreferredManufacturer: ArrayList<PreferredManufacturer> = ArrayList()
    val arrayListNewlyAdded: ArrayList<NewlyAdded> = ArrayList()
    val arraylistBanner1: ArrayList<Banner> = ArrayList()
    val arrayListMostPopular: ArrayList<MostPopular> = ArrayList()
    val arrayListTopRated: ArrayList<TopRated> = ArrayList()
    val arrayListFlashSale: ArrayList<FlashSale> = ArrayList()
    val arrayListDealOfDay: ArrayList<DealOfDay> = ArrayList()

    private lateinit var banner1Adapter: Banner1Adapter
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 4000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        shimmerShopFor = binding.shimmerViewShopFor
        shimmerManufacturer = binding.shimmerViewManufacturer
        shimmerMostPopular = binding.shimmerViewMostPopular
        setCurrentIndicator(0)
//        dashBoardList()

//        banner 2nd fun
        setBanner2ViewPager()
        setupIndicatorsBanner2()
        setCurrentIndicatorBanner2(0)
//      banner 3rd fun
        setBanner3ViewPager()
        setupIndicatorsBanner3()
        setCurrentIndicatorBanner3(0)

        binding.introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        val handler = Handler()
        val update = Runnable {
            binding.introSliderViewPager.setCurrentItem(currentPage % 11, true)
            binding.banner2ViewPager.setCurrentItem(currentPage % 4, true)
            binding.banner3ViewPager.setCurrentItem(currentPage % 4, true)
            currentPage++
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)

//        banner 2nd

        binding.banner2ViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicatorBanner2(position)
            }
        })

//        banner 3rd
        binding.banner3ViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicatorBanner3(position)
            }
        })

        /*    arrayList.clear()
            arrayList.add(CommonModel(view_type = 1, "Shop For", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Preferred Manufacturer", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 2, "Newly Added", "View All", 1, 1))
            arrayList.add(CommonModel(view_type = 2, "Popular", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Popular Brand", "View All", 0, 0))
            commonRecyclerViewAdapter = CommonRecyclerViewAdapter(requireContext(), arrayList)
            binding.recyclerView.adapter = commonRecyclerViewAdapter*/


        // adapter for shopFor
//        shopForAdapter = ShopForAdapter(requireContext(), arrayList[position].view_type)
//        binding.shopForRV.hasFixedSize()
//        binding.shopForRV.adapter = shopForAdapter


        // adapter for popular brand items
        popularBrandAdapter = PopularBrandAdapter(requireContext())
        binding.popularBrandRV.adapter = popularBrandAdapter
        binding.popularBrandRV.isNestedScrollingEnabled = false

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

        binding.newlyAddedAllTV.setOnClickListener {
            val intent = Intent(context, ViewAllActivity::class.java)
            startActivity(intent)
        }
        binding.mostPopularAllTV.setOnClickListener {
            val intent = Intent(context, ViewAllActivity::class.java)
            startActivity(intent)
        }


        /* imageListBanner.clear()
         imageListBanner.add(SlideModel(R.drawable.banner_2))
         imageListBanner.add(SlideModel(R.drawable.banner_1))
         imageListBanner.add(SlideModel(R.drawable.banner_2))
         imageListBanner.add(SlideModel(R.drawable.banner_1))
         binding.imageSliderBanner.setImageList(imageListBanner, ScaleTypes.FIT)
 */
        /*  imageListBanner2.clear()
          imageListBanner2.add(SlideModel(R.drawable.shopping))
          imageListBanner2.add(SlideModel(R.drawable.shopping))
          imageListBanner2.add(SlideModel(R.drawable.shopping))
          imageListBanner2.add(SlideModel(R.drawable.shopping))
          imageListBanner2.add(SlideModel(R.drawable.shopping))
          binding.imageSliderBanner2.setImageList(imageListBanner2, ScaleTypes.FIT)*/

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
//        setIntroSliderViewPager()
//        setupIndicators()
//        setCurrentIndicator(0)
    }

    private fun dashBoardList() {

        val call: Call<DashBoardResponse> =
            RetrofitClient.instance!!.api.dashBoard()
        call.enqueue(object : Callback<DashBoardResponse> {
            override fun onResponse(
                call: Call<DashBoardResponse>,
                response: Response<DashBoardResponse>
            ) {
                shimmerShopFor.stopShimmer()
                shimmerShopFor.visibility = View.GONE
                shimmerManufacturer.stopShimmer()
                shimmerManufacturer.visibility = View.GONE
                shimmerMostPopular.stopShimmer()
                shimmerMostPopular.visibility = View.GONE
                val dashBoardResponse = response.body()
                if (response.isSuccessful) {

                    if (dashBoardResponse!!.status) {
                        arrayListShopFor.clear()
                        arrayListShopFor.addAll(dashBoardResponse.shopFor)
                        shopForAdapter = ShopForAdapter(requireContext(), arrayListShopFor)
                        binding.shopForRV.adapter = shopForAdapter
                        shopForAdapter.notifyDataSetChanged()

                        arrayListPreferredManufacturer.clear()
                        arrayListPreferredManufacturer.addAll(dashBoardResponse.preferredManufacturers)
                        manufacturerAdapter =
                            ManufacturerAdapter(requireContext(), arrayListPreferredManufacturer)
                        binding.manufacturerRV.adapter = manufacturerAdapter
                        binding.manufacturerRV.isNestedScrollingEnabled = false
                        manufacturerAdapter.notifyDataSetChanged()

                        arrayListNewlyAdded.clear()
                        arrayListNewlyAdded.addAll(dashBoardResponse.newlyAdded)
                        newlyAddedAdapter = NewlyAddedAdapter(requireContext(), arrayListNewlyAdded)
                        binding.newlyAddedRV.adapter = newlyAddedAdapter
                        binding.newlyAddedRV.isNestedScrollingEnabled = false
                        newlyAddedAdapter.notifyDataSetChanged()

                        arraylistBanner1.clear()
                        arraylistBanner1.addAll(dashBoardResponse.banners)
                        setupIndicators()

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

                        arrayListFlashSale.clear()
                        arrayListFlashSale.addAll(dashBoardResponse.flashSale)
                        flashSaleAdapter = FlashSaleAdapter(requireContext(), arrayListFlashSale)
                        binding.flashSaleRV.adapter = flashSaleAdapter
                        binding.flashSaleRV.isNestedScrollingEnabled = false
                        flashSaleAdapter.notifyDataSetChanged()

                        arrayListDealOfDay.clear()
                        arrayListDealOfDay.addAll(dashBoardResponse.dealOfDay)
                        dealOfTheDayAdapter =
                            DealOfTheDayAdapter(requireContext(), arrayListDealOfDay)
                        binding.popularRV.adapter = dealOfTheDayAdapter
                        binding.popularRV.isNestedScrollingEnabled = false
                        dealOfTheDayAdapter.notifyDataSetChanged()

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

    // banner 1
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
        banner1Adapter = Banner1Adapter(requireContext(), arraylistBanner1)
        binding.introSliderViewPager.adapter = banner1Adapter
        banner1Adapter.notifyDataSetChanged()
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

    private fun setBanner2ViewPager() {
        banner2Adapter = Banner2Adapter(
            listOf(
                Banner2Slide(R.drawable.banner_1),
                Banner2Slide(R.drawable.banner_2),
                Banner2Slide(R.drawable.banner_1),
                Banner2Slide(R.drawable.banner_2)
            )
        )
        binding.banner2ViewPager.adapter = banner2Adapter
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
            binding.indicatorsBanner3.addView(indicators[i])
        }
    }

    private fun setCurrentIndicatorBanner3(index: Int) {

        val childCount = binding.indicatorsBanner3.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsBanner3[i] as ImageView
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

    private fun setBanner3ViewPager() {
        banner3Adapter = Banner3Adapter(
            listOf(
                Banner3Slide(R.drawable.banner_1),
                Banner3Slide(R.drawable.banner_2),
                Banner3Slide(R.drawable.banner_1),
                Banner3Slide(R.drawable.banner_2)
            )
        )
        binding.banner3ViewPager.adapter = banner3Adapter
    }
}


