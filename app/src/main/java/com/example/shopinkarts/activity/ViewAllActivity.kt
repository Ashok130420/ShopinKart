package com.example.shopinkarts.activity


import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.databinding.ActivityViewAllBinding
import com.example.shopinkarts.fragments.HomeFragment
import com.example.shopinkarts.response.Product


class ViewAllActivity : AppCompatActivity() {

    lateinit var binding: ActivityViewAllBinding
    lateinit var newlyAddedAdapter: NewlyAddedAdapter
    lateinit var manufacturerAdapter: ManufacturerAdapter
    lateinit var dealOfTheDayAdapter: DealOfTheDayAdapter
    lateinit var popularBrandAdapter: PopularBrandAdapter
    lateinit var flashSaleAdapter: FlashSaleAdapter
    lateinit var mostPopularAdapter: MostPopularAdapter
    lateinit var topRatedAdapter: TopRatedAdapter
    lateinit var discountForYouAdapter: DiscountForYouAdapter
    lateinit var recommendedAdapter: RecommendedAdapter

    var particularItemId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_all)

        particularItemId = intent.extras!!.getString("particularItemId", "")

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
        popularBrandAdapter = PopularBrandAdapter(this,HomeFragment.arrayListPopularBrand)
        binding.viewAllRV.adapter = popularBrandAdapter
        popularBrandAdapter.notifyDataSetChanged()
    }

    private fun discountForYou() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.discount_for_you)
        discountForYouAdapter = DiscountForYouAdapter(this, HomeFragment.arrayListDiscountForYou)
        binding.viewAllRV.adapter = discountForYouAdapter
        discountForYouAdapter.notifyDataSetChanged()
    }

    private fun recommended() {
        binding.headerViewAll.titleTV.text = resources.getString(R.string.recommended)
        recommendedAdapter = RecommendedAdapter(this, HomeFragment.arrayListRecommended)
        binding.viewAllRV.adapter = recommendedAdapter
        recommendedAdapter.notifyDataSetChanged()
    }

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