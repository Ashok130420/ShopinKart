package com.example.shopinkarts.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.ProductBannerAdapter
import com.example.shopinkarts.adapter.SelectColorAdapter
import com.example.shopinkarts.adapter.SelectSizeAdapter
import com.example.shopinkarts.adapter.SimilarProductsAdapter
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivityProductDetailsBinding
import com.example.shopinkarts.model.SelectColorModel
import com.example.shopinkarts.model.SelectSizeModel
import com.example.shopinkarts.response.NewlyAdded
import com.example.shopinkarts.response.Product
import com.example.shopinkarts.response.ProductResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailsBinding
    lateinit var selectColorAdapter: SelectColorAdapter
    lateinit var selectSizeAdapter: SelectSizeAdapter
    lateinit var similarProductsAdapter: SimilarProductsAdapter
    lateinit var productBannerAdapter: ProductBannerAdapter
    var arrayListBanner: ArrayList<Product> = ArrayList()

    var arrayListSimilarProduct: ArrayList<NewlyAdded> = ArrayList()
    var arraySelectColor: ArrayList<SelectColorModel> = ArrayList()
    var arraySelectSize: ArrayList<SelectSizeModel> = ArrayList()
    private var cartCount: Int = 1
    var quantityCount: Int = 0
    var productId = ""
    var isFreeDelivery = ""
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 4000
    var currentNumber = 1
    var lastNumber = 0
    var colorInstanceColor = ""

    companion object {
        var pInstance: ProductDetailsActivity = ProductDetailsActivity()
        var colorSize = 0

        fun getInstance(): ProductDetailsActivity {
            colorSize
            return pInstance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        pInstance = this
        inActiveAddCard()

        /* selectColorAdapter= SelectColorAdapter(this,colorInstanceColor,arraySelectColor)
         colorInstanceColor = selectColorAdapter.colorInstance

         if (colorInstanceColor == "true") {
             activeAddCard()
         } else {
             inActiveAddCard()
         }
         Log.d("colorInstanceColor", colorInstanceColor)*/
        Log.d("colorSize", colorSize.toString())

        productId = intent.extras!!.getString("productId", "")
        Log.d("productId_productId", productId)

        productApi()


        binding.productViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.headerProductDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerProductDetails.titleTV.setOnClickListener {
            Log.d("colorSize", colorSize.toString())
        }

        binding.plusQuantityTV.setOnClickListener {
            lastNumber = currentNumber
            currentNumber++
            binding.quantityShowTV.text = lastNumber.toString()

            if (lastNumber >= 1) {
                binding.addToCartTV.isClickable = true
                binding.addToCartTV.setBackgroundResource(R.drawable.button_blue)
                Log.d("currentNumber", currentNumber.toString())
            }

        }

        binding.minusQuantityTV.setOnClickListener {
            currentNumber = lastNumber
            if (currentNumber > 0) {
                lastNumber--
            }

            if (lastNumber <= 1) {
                binding.addToCartTV.isClickable = false
                binding.addToCartTV.setBackgroundResource(R.drawable.button_grey)
            }

            binding.quantityShowTV.text = lastNumber.toString()
        }


        binding.productDescriptionHeaderCL.setOnClickListener {
            if (binding.productDescriptionDetailsCL.visibility == View.VISIBLE) {
                binding.productDescriptionDetailsCL.visibility = View.GONE
                binding.productDescriptionIV.rotation = 0F
            } else if (binding.productDescriptionDetailsCL.visibility == View.GONE) {
                binding.productDescriptionDetailsCL.visibility = View.VISIBLE
                binding.productDescriptionIV.rotation = 90F
            }
        }

        binding.productChecklistHeaderCL.setOnClickListener {
            if (binding.productCheckListDetailsCL.visibility == View.VISIBLE) {
                binding.productCheckListDetailsCL.visibility = View.GONE
                binding.productCheckListIV.rotation = 0F
            } else if (binding.productCheckListDetailsCL.visibility == View.GONE) {
                binding.productCheckListDetailsCL.visibility = View.VISIBLE
                binding.productCheckListIV.rotation = 90F
            }
        }

        binding.readReviewsHeaderCL.setOnClickListener {
            val intent = Intent(this, ReadReviewsActivity::class.java)
            startActivity(intent)
        }

        binding.deliveryInstructionHeaderCL.setOnClickListener {
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

    // banner
    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(productBannerAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT)
        layoutParms.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )
                this?.layoutParams = layoutParms
            }
            binding.indicatorsContainersProduct.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index: Int) {

        val childCount = binding.indicatorsContainersProduct.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorsContainersProduct[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_active)
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(applicationContext, R.drawable.indicator_inactive)
                )

            }
        }

    }

    fun autoSlide(size: Int) {
        val handler = Handler()
        val update = Runnable {
            binding.productViewPager.setCurrentItem(currentPage % size, true)

            currentPage++
        }
        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, DELAY_MS, PERIOD_MS)
    }

    private fun productApi() {
        val requestBody: MutableMap<String, String> = HashMap()
        requestBody["productId"] = productId

        val call: Call<ProductResponse> = RetrofitClient.instance!!.api.productApi(requestBody)
        call.enqueue(object : Callback<ProductResponse> {
            override fun onResponse(
                call: Call<ProductResponse>,
                response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse!!.status) {

                        binding.tShirtNameTV.text = productResponse.product.productName
                        binding.discountedPriceTV.text = "Rs ${productResponse.product.price}.00"
                        binding.discountTV.text = "${productResponse.product.discount} % OFF"
                        if (productResponse.product.stock <= 50) {
                            binding.unitesLeftTV.visibility = View.VISIBLE
                        } else {
                            binding.unitesLeftTV.visibility = View.GONE
                        }
                        val arrayBanner: ArrayList<String> = ArrayList()
                        arrayBanner.addAll(productResponse.product.productImages)
                        productBannerAdapter = ProductBannerAdapter(
                            this@ProductDetailsActivity,
                            arrayBanner
                        )
                        binding.productViewPager.adapter = productBannerAdapter
                        setupIndicators()
                        setCurrentIndicator(0)
                        if (arrayBanner.isNotEmpty())
                            autoSlide(arrayBanner.size)
                        binding.dispatchedTV.text = productResponse.product.dispatchDetails[0]
                        binding.deliveryTV.text = productResponse.product.dispatchDetails[1]
                        binding.ratingBar.rating = productResponse.product.avgRating.toFloat()

                        isFreeDelivery = productResponse.product.isFreeDelivery.toString()
                        if (isFreeDelivery == "1") {
                            binding.deliveryIV.visibility = View.VISIBLE
                            binding.deliveryTV.visibility = View.VISIBLE
                        } else {
                            binding.deliveryIV.visibility = View.GONE
                            binding.deliveryTV.visibility = View.GONE
                        }


                        binding.productDescriptionDetailsTV.text =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(
                                    productResponse.product.description,
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            } else {
                                Html.fromHtml(productResponse.product.description)
                            }
                        binding.productCheckListDetailsTV.text =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(
                                    " ${productResponse.product.productCheckList.box}  ${productResponse.product.productCheckList.color}",
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            } else {
                                Html.fromHtml(productResponse.product.productCheckList.toString())
                            }
                        binding.deliveryInstructionDetailsTV.text =
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                Html.fromHtml(
                                    productResponse.product.deliveryInstructions[0],
                                    Html.FROM_HTML_MODE_COMPACT
                                )
                            } else {
                                Html.fromHtml(productResponse.product.deliveryInstructions[0])
                            }

                        arrayListSimilarProduct.clear()
                        arrayListSimilarProduct.addAll(productResponse.similarProducts)
                        similarProductsAdapter = SimilarProductsAdapter(
                            this@ProductDetailsActivity,
                            arrayListSimilarProduct
                        )
                        binding.similarProductsRV.adapter = similarProductsAdapter
                        binding.similarProductsRV.isNestedScrollingEnabled = false
                        similarProductsAdapter.notifyDataSetChanged()


                        for (element in productResponse.product.attributes) {
                            if (element.name == "Color") {
                                arraySelectColor.clear()
                                for (item in element.types) {
                                    arraySelectColor.add(SelectColorModel(item))
                                    Log.d("colorSizeSize", colorSize.toString())
                                }
                            }
                        }
                        selectColorAdapter =
                            SelectColorAdapter(this@ProductDetailsActivity, "", arraySelectColor)
                        binding.selectColorRV.adapter = selectColorAdapter
                        binding.selectColorRV.isNestedScrollingEnabled = false

                        selectColorAdapter.notifyDataSetChanged()

                        for (element in productResponse.product.attributes) {
                            if (element.name == "Size") {
                                arraySelectSize.clear()
                                for (item in element.types) {

                                    arraySelectSize.add(SelectSizeModel(item))

                                }
                            }
                        }

                        selectSizeAdapter =
                            SelectSizeAdapter(this@ProductDetailsActivity, arraySelectSize)
                        binding.selectSizeRV.adapter = selectSizeAdapter
                        binding.selectSizeRV.isNestedScrollingEnabled = false
                        selectSizeAdapter.notifyDataSetChanged()


                        Log.e("TAG", "${response.message()} ")
                    }
                } else {

                    Toast.makeText(
                        this@ProductDetailsActivity,
                        "${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(
                    this@ProductDetailsActivity,
                    "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "${t.message} ")
            }
        })
    }

    fun inActiveAddCard() {

        binding.addToCartTV.isClickable = false
        binding.addToCartTV.setBackgroundResource(R.drawable.button_grey)
    }


    fun activeAddCard() {

        binding.addToCartTV.isClickable = true

        binding.addToCartTV.setBackgroundResource(R.drawable.button_blue)

        binding.addToCartTV.setOnClickListener {
            binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE
            binding.headerProductDetails.cartItemTV.text =
                cartCount++.toString()

        }
    }

}