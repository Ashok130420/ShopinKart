package com.example.shopinkarts.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.activity.ViewAllActivity
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.databinding.FragmentHomeBinding
import com.example.shopinkarts.model.ClothShortingModel
import com.example.shopinkarts.model.CommonModel


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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        /*    arrayList.clear()
            arrayList.add(CommonModel(view_type = 1, "Shop For", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Preferred Manufacturer", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 2, "Newly Added", "View All", 1, 1))
            arrayList.add(CommonModel(view_type = 2, "Popular", "View All", 0, 0))
            arrayList.add(CommonModel(view_type = 3, "Popular Brand", "View All", 0, 0))
            commonRecyclerViewAdapter = CommonRecyclerViewAdapter(requireContext(), arrayList)
            binding.recyclerView.adapter = commonRecyclerViewAdapter*/

        imageList.clear()
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))
        imageList.add(SlideModel(R.drawable.banner_1))
        imageList.add(SlideModel(R.drawable.banner_2))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

        // adapter for shopFor
//        shopForAdapter = ShopForAdapter(requireContext(), arrayList[position].view_type)
//        binding.shopForRV.hasFixedSize()
//        binding.shopForRV.adapter = shopForAdapter

        shopForAdapter = ShopForAdapter(requireContext())
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


}