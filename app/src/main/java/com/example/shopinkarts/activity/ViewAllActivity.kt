package com.example.shopinkarts.activity


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivityViewAllBinding
import com.example.shopinkarts.fragments.HomeFragment
import com.example.shopinkarts.response.ParticularItemResponse
import com.example.shopinkarts.response.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ViewAllActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewAllBinding
    lateinit var filterItemsAdapter: FilterItemsAdapter
    lateinit var newlyAddedAdapter: NewlyAddedAdapter
    lateinit var manufacturerAdapter: ManufacturerAdapter
    lateinit var dealOfTheDayAdapter: DealOfTheDayAdapter
    lateinit var popularBrandAdapter: PopularBrandAdapter
    lateinit var flashSaleAdapter: FlashSaleAdapter
    lateinit var clothsShortingAdapter: ClothsShortingAdapter
    lateinit var mostPopularAdapter: MostPopularAdapter
    lateinit var topRatedAdapter: TopRatedAdapter
    lateinit var discountForYouAdapter: DiscountForYouAdapter
    lateinit var recommendedAdapter: RecommendedAdapter
    lateinit var particularItemAdapter:ParticularItemAdapter

    var arrayListParticularItem: ArrayList<Product> = ArrayList()

    var particularItemId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all)

        particularItemId = intent.extras!!.getString("particularItemId", "")

//        binding.headerViewAll.titleTV.text = resources.getString(R.string.newly_added)
        binding.headerViewAll.iconIV.visibility = View.GONE
        binding.headerViewAll.cartIV.visibility = View.GONE
        binding.headerViewAll.cartItemTV.visibility = View.GONE

        binding.headerViewAll.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.sortCL.setOnClickListener {
            popupMenuSorting(it)
        }

        binding.filterCL.setOnClickListener {
            popupMenuFilter(it)
        }


        intent.extras?.let {
            if (it.getString("from") == "newlyAdded") {
                newlyAdded()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "mostPopular") {
                mostPopular()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "preferredManufacturer") {
                preferredManufacturer()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "topRated") {
                topRated()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "flashSale") {
                flashSale()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "dealOfDay") {
                dealOfDay()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "popularBrand") {
                popularBrand()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "discountForYou") {
                discountForYou()
            }
        }
        intent.extras?.let {
            if (it.getString("from") == "recommended") {
                recommended()
            }
        }

    }

    private fun newlyAdded() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.newly_added)
        newlyAddedAdapter = NewlyAddedAdapter(this, HomeFragment.arrayListNewlyAdded)
        binding.viewAllRV.adapter = newlyAddedAdapter
        newlyAddedAdapter.notifyDataSetChanged()
    }

    private fun mostPopular() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.most_popular)
        mostPopularAdapter = MostPopularAdapter(this, HomeFragment.arrayListMostPopular)
        binding.viewAllRV.adapter = mostPopularAdapter
        mostPopularAdapter.notifyDataSetChanged()
    }

    private fun preferredManufacturer() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.preferred_manufacturer)
        manufacturerAdapter = ManufacturerAdapter(this, HomeFragment.arrayListPreferredManufacturer)
        binding.viewAllRV.adapter = manufacturerAdapter
        manufacturerAdapter.notifyDataSetChanged()
    }

    private fun topRated() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.top_rated)
        topRatedAdapter = TopRatedAdapter(this, HomeFragment.arrayListTopRated)
        binding.viewAllRV.adapter = topRatedAdapter
        topRatedAdapter.notifyDataSetChanged()
    }

    private fun flashSale() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.flash_sale)
        flashSaleAdapter = FlashSaleAdapter(this, HomeFragment.arrayListFlashSale)
        binding.viewAllRV.adapter = flashSaleAdapter
        flashSaleAdapter.notifyDataSetChanged()
    }

    private fun dealOfDay() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.deal_of_the_day)
        dealOfTheDayAdapter = DealOfTheDayAdapter(this, HomeFragment.arrayListDealOfDay)
        binding.viewAllRV.adapter = dealOfTheDayAdapter
        dealOfTheDayAdapter.notifyDataSetChanged()
    }

    private fun popularBrand() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.popular_brand)
        popularBrandAdapter = PopularBrandAdapter(this)
        binding.viewAllRV.adapter = popularBrandAdapter
        popularBrandAdapter.notifyDataSetChanged()
    }

    private fun discountForYou() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.discount_for_you)
        discountForYouAdapter = DiscountForYouAdapter(this)
        binding.viewAllRV.adapter = discountForYouAdapter
        discountForYouAdapter.notifyDataSetChanged()
    }

    private fun recommended() {
        recommendedAdapter = RecommendedAdapter(this)
        binding.viewAllRV.adapter = recommendedAdapter
        recommendedAdapter.notifyDataSetChanged()
    }

  /*  private fun particularItemList(type: String, subType: String, value: String) {


        val call: Call<ParticularItemResponse> =
            RetrofitClient.instance!!.api.particularItem(
                particularItemId,
                type = type,
                subType = subType,
                value = value
            )
        call.enqueue(object : Callback<ParticularItemResponse> {
            override fun onResponse(
                call: Call<ParticularItemResponse>,
                response: Response<ParticularItemResponse>
            ) {
                val particularItemResponse = response.body()
                if (response.isSuccessful) {
                    if (particularItemResponse!!.status) {

//                        intent.extras?.let {
//                            if (it.getString("from") == "newlyAdded") {
//                                binding.headerViewAll.titleTV.text = resources.getString(R.string.newly_added)
////                                HomeFragment.arrayListNewlyAdded.addAll(particularItemResponse.products)
//                                newlyAddedAdapter = NewlyAddedAdapter(this@ViewAllActivity, HomeFragment.arrayListNewlyAdded)
//                                binding.viewAllRV.adapter = newlyAddedAdapter
//                                newlyAddedAdapter.notifyDataSetChanged()
//                            }
//                        }
                        arrayListParticularItem.clear()
                        arrayListParticularItem.addAll(particularItemResponse.products)
                        particularItemAdapter = ParticularItemAdapter(
                            this@ParticularItemActivity, arrayListParticularItem
                        )
                        binding.particularItemRV.adapter = particularItemAdapter
                        particularItemAdapter.notifyDataSetChanged()

                    }
                    Log.d("TAG", "onResponse_SuccessResponse${particularItemResponse.message} ")
                } else {
                    Toast.makeText(
                        this@ViewAllActivity,
                        "${particularItemResponse?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ParticularItemResponse>, t: Throwable) {
                Log.d("TAG", "onFailureResponse: ${t.message}")
                Toast.makeText(this@ViewAllActivity, "${t.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }*/

    private fun popupMenuSorting(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.sorting_option_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.optionPriceHigh -> {
//                    particularItemList("sort", "price", "0")
                    true
                }
                R.id.optionPriceLow -> {
//                    particularItemList("sort", "price", "1")
                    true
                }
                R.id.optionNewest -> {
//                    particularItemList("sort", "newest", "")
                    true
                }
                else -> true
            }
        }
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()

    }

    private fun popupMenuFilter(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.filter_option_menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.optionFilter1 -> {
//                    particularItemList("filter", "price", "0")

                    true
                }
                R.id.optionFilter2 -> {
//                    particularItemList("filter", "price", "1")

                    true
                }
                R.id.optionFilter3 -> {
//                    particularItemList("filter", "price", "2")

                    true
                }
                R.id.optionFilter4 -> {

//                    particularItemList("filter", "price", "3")
                    true
                }
                else -> true
            }
        }
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenu)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
            .invoke(menu, true)
        popupMenu.show()

    }

}