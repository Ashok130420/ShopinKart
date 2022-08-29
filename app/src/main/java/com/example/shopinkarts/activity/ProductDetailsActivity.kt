package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.ReviewsAdapter
import com.example.shopinkarts.adapter.SelectColorAdapter
import com.example.shopinkarts.adapter.SelectSizeAdapter
import com.example.shopinkarts.adapter.SimilarProductsAdapter
import com.example.shopinkarts.databinding.ActivityProductDetailsBinding
import com.example.shopinkarts.model.SelectColorModel
import com.example.shopinkarts.model.SelectSizeModel

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailsBinding
    lateinit var selectColorAdapter: SelectColorAdapter
    lateinit var selectSizeAdapter: SelectSizeAdapter
    lateinit var similarProductsAdapter: SimilarProductsAdapter
    lateinit var reviewsAdapter: ReviewsAdapter
    var arraySelectColor: ArrayList<SelectColorModel> = ArrayList()
    var arraySelectSize: ArrayList<SelectSizeModel> = ArrayList()
    private var cartCount: Int = 1
    var quantityCount: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)

        binding.headerProductDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.addToCartTV.setOnClickListener {
            binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE
            binding.headerProductDetails.cartItemTV.text = cartCount++.toString()
        }
        binding.plusQuantityTV.setOnClickListener {
            binding.quantityShowTV.text = quantityCount++.toString()
        }
        binding.minusQuantityTV.setOnClickListener {
            binding.quantityShowTV.text = quantityCount--.toString()
        }

        val imageList = ArrayList<SlideModel>()
        imageList.clear()
        imageList.add(SlideModel(R.drawable.newly_added_image))
        imageList.add(SlideModel(R.drawable.newly_added_image))
        imageList.add(SlideModel(R.drawable.newly_added_image))
        imageList.add(SlideModel(R.drawable.newly_added_image))

        binding.productDetailsIS.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        // add values of array list of color
        arraySelectColor.add(
            SelectColorModel(
                colors = "#C2E2B4"
            )
        )
        arraySelectColor.add(
            SelectColorModel(
                colors = "#229D4C"
            )
        )
        arraySelectColor.add(
            SelectColorModel(
                colors = "#F6F6F6"
            )
        )
        arraySelectColor.add(
            SelectColorModel(
                colors = "#EE4C41"
            )
        )

        // adapter for select color
        selectColorAdapter = SelectColorAdapter(this, arraySelectColor)
        binding.selectColorRV.adapter = selectColorAdapter
        binding.selectColorRV.isNestedScrollingEnabled = false

        // add values of array list of size
        arraySelectSize.add(
            SelectSizeModel(
                sizes = "S"
            )
        )
        arraySelectSize.add(
            SelectSizeModel(
                sizes = "M"
            )
        )
        arraySelectSize.add(
            SelectSizeModel(
                sizes = "L"
            )
        )
        arraySelectSize.add(
            SelectSizeModel(
                sizes = "XL"
            )
        )
        arraySelectSize.add(
            SelectSizeModel(
                sizes = "XXL"
            )
        )

        // adapter for select size
        selectSizeAdapter = SelectSizeAdapter(this, arraySelectSize)
        binding.selectSizeRV.adapter = selectSizeAdapter
        binding.selectSizeRV.isNestedScrollingEnabled = false

        // adapter for similar products
        similarProductsAdapter = SimilarProductsAdapter(this)
        binding.similarProductsRV.adapter = similarProductsAdapter
        binding.similarProductsRV.isNestedScrollingEnabled = false

        // adapter for reviews
        reviewsAdapter = ReviewsAdapter(this)
        binding.reviewsRV.adapter = reviewsAdapter
        binding.reviewsRV.isNestedScrollingEnabled = false


        binding.productDescriptionCL.setOnClickListener {
            if (binding.productDescriptionDetailsCL.visibility == View.VISIBLE) {
                binding.productDescriptionDetailsCL.visibility = View.GONE
                binding.productDescriptionIV.rotation = 0F
            } else if (binding.productDescriptionDetailsCL.visibility == View.GONE) {
                binding.productDescriptionDetailsCL.visibility = View.VISIBLE
                binding.productDescriptionIV.rotation = 90F
            }
        }

        binding.productChecklistCL.setOnClickListener {
            if (binding.productCheckListDetailsCL.visibility == View.VISIBLE) {
                binding.productCheckListDetailsCL.visibility = View.GONE
                binding.productCheckListIV.rotation = 0F
            } else if (binding.productCheckListDetailsCL.visibility == View.GONE) {
                binding.productCheckListDetailsCL.visibility = View.VISIBLE
                binding.productCheckListIV.rotation = 90F
            }
        }

        binding.readReviewsHeaderCL.setOnClickListener {
            if (binding.readReviewsDetailsCL.visibility == View.VISIBLE) {
                binding.readReviewsDetailsCL.visibility = View.GONE
                binding.reviewHeaderView.visibility = View.VISIBLE
                binding.readReviewsIV.rotation = 0F
            } else if (binding.readReviewsDetailsCL.visibility == View.GONE) {
                binding.readReviewsDetailsCL.visibility = View.VISIBLE
                binding.reviewHeaderView.visibility = View.GONE
                binding.readReviewsIV.rotation = 90F
            }
        }

        binding.deliveryInstructionCL.setOnClickListener {
            if (binding.deliveryInstructionDetailsCL.visibility == View.VISIBLE) {
                binding.deliveryInstructionDetailsCL.visibility = View.GONE
                binding.deliveryInstructionIV.rotation = 0F
            } else if (binding.deliveryInstructionDetailsCL.visibility == View.GONE) {
                binding.deliveryInstructionDetailsCL.visibility = View.VISIBLE
                binding.deliveryInstructionIV.rotation = 90F
            }
        }

        binding.buyNowTV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }
        binding.headerProductDetails.cartIV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }
    }
}