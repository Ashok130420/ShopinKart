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
import com.example.shopinkarts.adapter.*
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.databinding.ActivityProductDetailsBinding
import com.example.shopinkarts.model.CartModel
import com.example.shopinkarts.model.SelectColorModel
import com.example.shopinkarts.model.SelectSizeModel
import com.example.shopinkarts.response.NewlyAdded
import com.example.shopinkarts.response.ProductResponse
import com.example.shopinkarts.response.VariantsArr
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class ProductDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductDetailsBinding
    lateinit var selectColorAdapter: SelectColorAdapter
    lateinit var selectSizeAdapter: SelectSizeAdapter
    lateinit var similarProductsAdapter: SimilarProductsAdapter
    lateinit var productBannerAdapter: ProductBannerAdapter

    var arrayListSimilarProduct: ArrayList<NewlyAdded> = ArrayList()
    var arraySelectColor: ArrayList<SelectColorModel> = ArrayList()
    var arraySelectSize: ArrayList<SelectSizeModel> = ArrayList()
    private var cartCount: Int = 1
    var productId = ""
    var isFreeDelivery = ""
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 4000

    var pId = ""
    var vId = ""
    var itemName = ""
    var discountedPrice = 0
    var actualPrice = ""
    var color = ""
    var size = ""
    var quantity = ""
    var totalAmount = 0
    var imageUrl = ""
    var variantTarget = ""

    companion object {
        var pInstance: ProductDetailsActivity = ProductDetailsActivity()
        var arrayListVariant: ArrayList<VariantsArr> = ArrayList()
        var colorSize = 0
        var selectedColor = ""
        var selectedSize = ""
        var sizeOfSize = 0
        var quantitySze = 0
        var stock = 0
        var currentNumber = 1
        var lastNumber = 0

        fun getInstance(): ProductDetailsActivity {
            return pInstance
        }
    }

    override fun onResume() {
        DashBoardActivity.arrayListCart
        Log.d("arrayListCart", DashBoardActivity.arrayListCart.toString())
        if (DashBoardActivity.arrayListCart.isNotEmpty()) {
            binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE
            binding.headerProductDetails.cartItemTV.text =
                DashBoardActivity.arrayListCart.size.toString()
        } else {
            binding.headerProductDetails.cartItemTV.visibility = View.GONE
        }
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        pInstance = this


        productId = intent.extras!!.getString("productId", "")
        Log.d("productId_productId", productId)


        if (DashBoardActivity.arrayListCart.isNotEmpty()) {

            binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE

            binding.headerProductDetails.cartItemTV.text =
                DashBoardActivity.arrayListCart.size.toString()

        } else {
            binding.headerProductDetails.cartItemTV.visibility = View.GONE
        }
        colorSizeUpdate()
        productApi()
        inActiveAddCart()

        binding.productViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        binding.headerProductDetails.backIV.setOnClickListener {
            lastNumber = 0
            currentNumber = 0
            onBackPressed()
        }
        binding.headerProductDetails.titleTV.setOnClickListener {
            Log.d("ColoR", "onCreate: $selectedColor")
        }

        binding.plusQuantityTV.setOnClickListener {
            if (currentNumber <= stock) {

                lastNumber = currentNumber
                currentNumber++
                binding.quantityShowTV.text = lastNumber.toString()

                quantitySze = if (lastNumber >= 1) 1 else 0
                Log.d("quantitySze", quantitySze.toString())

                if (lastNumber >= 1) {
                    activeAddCart()
                }
            } else {
                Toast.makeText(this, "Out of stock", Toast.LENGTH_SHORT).show()
            }
        }

        binding.minusQuantityTV.setOnClickListener {

            currentNumber = lastNumber
            if (currentNumber > 0) {
                lastNumber--
            }
            if (lastNumber < 1) {
                inActiveAddCart()
            }
            quantitySze = if (lastNumber <= 1) 0 else 1
            Log.d("quantitySze", quantitySze.toString())
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

        binding.headerProductDetails.cartIV.setOnClickListener {
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)
        }

        binding.buyNowTV.setOnClickListener {
            activeAddCart()

            arrayListVariant
           /* if (DashBoardActivity.selectedVIDs.contains(vId)) {

                Toast.makeText(this, "Product already into cart ", Toast.LENGTH_SHORT).show()

            } else*/
            if (DashBoardActivity.arrayListCart.isEmpty()) {
                addItem()
                val intent = Intent(this, ProductCartActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Product already in cart ", Toast.LENGTH_SHORT).show()
            }

        }

        binding.addToCartTV.setOnClickListener {

            /* variantTarget = "${selectedColor}-${selectedSize}"
             totalAmount = discountedPrice * lastNumber

             if (DashBoardActivity.arrayListCart.isEmpty()) {

                 DashBoardActivity.arrayListCart.add(
                     CartModel(
                         pId = pId,
                         vId = vId,
                         itemName = itemName,
                         discountedPrice = "Rs ${totalAmount}.00",
                         actualPrice = "",
                         color = selectedColor,
                         size = selectedSize,
                         quantity = lastNumber.toString(),
                         totalAmount = totalAmount,
                         imageUrl = imageUrl
                     )
                 )
                 DashBoardActivity.selectedVIDs.add(vId)

                 Log.d("elementsVid", vId)

                 //store both the arraylist in SP

             } else {

                 if (DashBoardActivity.selectedVIDs.contains(vId)) {
                     //do nothing
                     Toast.makeText(this, "Already Exist", Toast.LENGTH_SHORT).show()
                 } else {
                     DashBoardActivity.arrayListCart.add(
                         CartModel(
                             pId = pId,
                             vId = vId,
                             itemName = itemName,
                             discountedPrice = "Rs ${totalAmount}.00",
                             actualPrice = "",
                             color = selectedColor,
                             size = selectedSize,
                             quantity = lastNumber.toString(),
                             totalAmount = totalAmount,
                             imageUrl = imageUrl
                         )
                     )
                     DashBoardActivity.selectedVIDs.add(vId)
                     Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT)
                         .show()

                     //store both the arraylist in SP
                 }


             }
             Log.d("variantTarget", variantTarget)
             binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE
             binding.headerProductDetails.cartItemTV.text =
                 DashBoardActivity.arrayListCart.size.toString()*/
            addItem()

        }

    }


    // banner
    private fun setupIndicators() {
        val indicators = arrayOfNulls<ImageView>(productBannerAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ListPopupWindow.WRAP_CONTENT,
                ListPopupWindow.WRAP_CONTENT
            )
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

    private fun addItem() {
        variantTarget = "${selectedColor}-${selectedSize}"
        totalAmount = discountedPrice * lastNumber

        if (DashBoardActivity.arrayListCart.isEmpty()) {

            DashBoardActivity.arrayListCart.add(
                CartModel(
                    pId = pId,
                    vId = vId,
                    itemName = itemName,
                    discountedPrice = "Rs ${totalAmount}.00",
                    actualPrice = "",
                    color = selectedColor,
                    size = selectedSize,
                    quantity = lastNumber.toString(),
                    totalAmount = totalAmount,
                    imageUrl = imageUrl
                )
            )
            DashBoardActivity.selectedVIDs.add(vId)

            Log.d("elementsVid", vId)

            //store both the arraylist in SP

        } else {

            if (DashBoardActivity.selectedVIDs.contains(vId)) {
                //do nothing
                Toast.makeText(this, "Product already in cart", Toast.LENGTH_SHORT).show()
            } else {
                DashBoardActivity.arrayListCart.add(
                    CartModel(
                        pId = pId,
                        vId = vId,
                        itemName = itemName,
                        discountedPrice = "Rs ${totalAmount}.00",
                        actualPrice = "",
                        color = selectedColor,
                        size = selectedSize,
                        quantity = lastNumber.toString(),
                        totalAmount = totalAmount,
                        imageUrl = imageUrl
                    )
                )
                DashBoardActivity.selectedVIDs.add(vId)
                Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT)
                    .show()

                //store both the arraylist in SP
            }

        }
        Log.d("variantTarget", variantTarget)
        binding.headerProductDetails.cartItemTV.visibility = View.VISIBLE
        binding.headerProductDetails.cartItemTV.text =
            DashBoardActivity.arrayListCart.size.toString()
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


                        pId = productResponse.product._id
                        itemName = productResponse.product.productName
                        imageUrl = productResponse.product.productImages[0]
                        binding.tShirtNameTV.text = productResponse.product.productName
                        binding.discountedPriceTV.text =
                            "Rs ${productResponse.product.price}.00"
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

                                }
                            }
                        }
                        selectColorAdapter =
                            SelectColorAdapter(this@ProductDetailsActivity, arraySelectColor)
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

                        arrayListVariant.addAll(productResponse.product.variantsArr)




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

    fun colorSizeUpdate() {
        colorSize
        selectedColor
        totalAmount = discountedPrice * lastNumber
        variantTarget = "${selectedColor}-${selectedSize}"
        Log.d("variantTarget", variantTarget)
        Log.d("colorSelect1", selectedColor)
        updatePrice()
    }

    fun sizeUpdate() {
        sizeOfSize
        selectedSize
        totalAmount = discountedPrice * lastNumber
        variantTarget = "${selectedColor}-${selectedSize}"
        Log.d("variantTarget", variantTarget)
        Log.d("selectedSize", sizeOfSize.toString())
        updatePrice()
    }

    fun inActiveAddCart() {

        binding.addToCartTV.isEnabled = false
        binding.buyNowTV.isEnabled = false
        binding.buyNowTV.isEnabled = false
        binding.plusQuantityTV.isEnabled = false
        binding.minusQuantityTV.isEnabled = false
        binding.addToCartTV.setBackgroundResource(R.drawable.button_grey)
    }

    fun activeAddCart() {

        if (colorSize == 1 && sizeOfSize == 1 && quantitySze == 1) {
            binding.addToCartTV.isEnabled = true
            binding.buyNowTV.isEnabled = true
            binding.addToCartTV.setBackgroundResource(R.drawable.button_blue)


        }
    }

    private fun updatePrice() {

        if (colorSize == 1 && sizeOfSize == 1) {

            binding.plusQuantityTV.isEnabled = true
            binding.minusQuantityTV.isEnabled = true

            for (elements in arrayListVariant) {
                Log.d("elements", "onResponse: $elements")
                if (elements.variant == variantTarget) {

                    Log.d("PlusE", elements.variant)
                    Log.d("PlusV", variantTarget)
                    vId = elements.id
                    Log.d("vId", elements.id)
                    binding.discountedPriceTV.text = "Rs ${elements.price}.00"
                    discountedPrice = elements.price
//                    totalAmount = arrayListVariant.sumBy { it.price }
                    stock = elements.stock
                    Log.d("Stock", stock.toString())
                }

            }
        }
    }


    fun updateLastNumber() {
        lastNumber = 0
        currentNumber = 1
        binding.quantityShowTV.text = lastNumber.toString()
    }

}
