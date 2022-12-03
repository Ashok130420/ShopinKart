package com.app.shopinkarts.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.app.shopinkarts.R
import com.app.shopinkarts.adapter.*
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityProductDetailsBinding
import com.app.shopinkarts.model.CartModel
import com.app.shopinkarts.model.SelectColorModel
import com.app.shopinkarts.model.SelectSizeModel
import com.app.shopinkarts.response.*
import org.json.JSONObject
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
    lateinit var sharedPreference: SharedPreference

    var arrayListSimilarProduct: ArrayList<SimilarProduct> = ArrayList()
    var arraySelectColor: ArrayList<SelectColorModel> = ArrayList()
    var arraySelectSize: ArrayList<SelectSizeModel> = ArrayList()
    var arrayListVariant: ArrayList<VariantsArr> = ArrayList()


    var isFreeDelivery = ""
    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 2000
    val PERIOD_MS: Long = 4000
    var imageNumber = 1

    var dType = 0
    var dPrice = 0
    var dDiscount = 0

    companion object {
        var pInstance: ProductDetailsActivity = ProductDetailsActivity()
        var colorSize = 0
        var selectedColor = ""
        var selectedSize = ""
        var sizeOfSize = 0
        var quantitySze = 0
        var stock = 0
        var currentNumber = 0
        var totalAmount = 0
        var discountedPrice = 0
        var actualPrice = 0
        var productId = ""
        var pId = ""
        var vId = ""
        var itemName = ""
        var color = ""
        var size = ""
        var imageUrl = ""
        var variantTarget = ""
        var unitPrice = 0

        fun getInstance(): ProductDetailsActivity {
            return pInstance
        }
    }

    override fun onResume() {
        binding.quantityShowTV.text = currentNumber.toString()
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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        pInstance = this

        sharedPreference = SharedPreference(this)

        currentNumber = 0

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
            currentNumber = 0
            colorSize = 0
            sizeOfSize = 0
            quantitySze = 0
            onBackPressed()
        }

        binding.headerProductDetails.titleTV.setOnClickListener {
            Log.d("ColoR", "onCreate: $selectedColor")
        }

        binding.plusQuantityTV.setOnClickListener {

            if (currentNumber < stock) {

                currentNumber++

                binding.quantityShowTV.text = currentNumber.toString()

                binding.discountedPriceTV.text = "Rs ${unitPrice * currentNumber}.00"

                quantitySze = if (currentNumber > 1) {
                    activeAddCart()
                    1
                } else {
                    0
                }

            } else {
                Toast.makeText(this, "Out of stock", Toast.LENGTH_SHORT).show()
            }
        }

        binding.minusQuantityTV.setOnClickListener {
            if (currentNumber > 1) {
                currentNumber--
            }
            if (currentNumber < 1) {
                inActiveAddCart()
            }
            quantitySze = if (currentNumber <= 1) 0 else 1
            Log.d("quantitySze", quantitySze.toString())
            binding.quantityShowTV.text = currentNumber.toString()

            binding.discountedPriceTV.text = "Rs ${unitPrice * currentNumber}.00"

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
            addItem()
            val intent = Intent(this, ProductCartActivity::class.java)
            startActivity(intent)


        }

        binding.addToCartTV.setOnClickListener {

            addItem()


        }

    }


    // banner
    private fun setupIndicators() {

        val indicators = arrayOfNulls<ImageView>(productBannerAdapter.itemCount)
        val layoutParms: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT
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
        totalAmount = discountedPrice * currentNumber

        val variantArray: ArrayList<Variants> = ArrayList()

        if (DashBoardActivity.arrayListCart.isEmpty()) {
            val variant = Variants(
                color = selectedColor,
                size = selectedSize,
                quantity = currentNumber,
                price = actualPrice,
                id = vId,
                stock = stock
            )
            variantArray.add(variant)
            val product = CartModel(
                pId = pId,
                vId = vId,
                itemName = itemName,
                discountedPrice = discountedPrice.toString(),
                actualPrice = actualPrice,
                color = selectedColor,
                size = selectedSize,
                quantity = currentNumber,
                totalAmount = totalAmount,
                imageUrl = imageUrl,
                stock = stock,
                variantsArr = arrayListVariant,
                variants = variantArray
            )
            DashBoardActivity.arrayListCart.add(product)
            DashBoardActivity.selectedVIDs.add(vId)
            Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show()

            Log.d("elementsVid", vId)

            //store arraylist in SP
            sharedPreference.setArray()

        } else {

            var isProductFound = false

            for (index in DashBoardActivity.arrayListCart.indices) {
                val product = DashBoardActivity.arrayListCart[index]
                if (product.pId == pId) {
                    isProductFound = true
                    if (product.variants.contains(
                            Variants(
                                color = selectedColor,
                                size = selectedSize,
                                quantity = currentNumber,
                                price = actualPrice,
                                id = vId,
                                stock = stock
                            )
                        )
                    ) {
                        Toast.makeText(this, "Product already in cart", Toast.LENGTH_SHORT).show()
                        //sharedPreference.setArray()
                    } else {
                        variantArray.clear()
                        variantArray.addAll(product.variants)
                        var variantCheck = false

                        for (ind in variantArray.indices) {
                            if (product.variants[ind].id == vId) {
                                variantCheck = true
                                variantArray.set(
                                    index = ind, element = Variants(
                                        color = selectedColor,
                                        size = selectedSize,
                                        quantity = currentNumber,
                                        price = actualPrice,
                                        id = vId,
                                        stock = stock
                                    )
                                )
                                break
                            }
                        }
                        if (!variantCheck) {
                            variantArray.add(
                                Variants(
                                    color = selectedColor,
                                    size = selectedSize,
                                    quantity = currentNumber,
                                    price = actualPrice,
                                    id = vId,
                                    stock = stock
                                )
                            )
                        }
                        DashBoardActivity.arrayListCart.set(
                            index = index, element = CartModel(
                                pId = pId,
                                vId = vId,
                                itemName = itemName,
                                discountedPrice = discountedPrice.toString(),
                                actualPrice = actualPrice,
                                color = selectedColor,
                                size = selectedSize,
                                quantity = currentNumber,
                                totalAmount = totalAmount,
                                imageUrl = imageUrl,
                                stock = stock,
                                variantsArr = arrayListVariant,
                                variants = variantArray
                            )
                        )
                        Toast.makeText(this, "Product update Successfully", Toast.LENGTH_SHORT).show()

                        Log.d("itemDetails.variants", DashBoardActivity.arrayListCart.toString())

                        sharedPreference.setArray()
                    }


                    break
                }
            }

            if (!isProductFound) {
                variantArray.add(
                    Variants(
                        color = selectedColor,
                        size = selectedSize,
                        quantity = currentNumber,
                        price = actualPrice,
                        id = vId,
                        stock = stock
                    )
                )
                DashBoardActivity.arrayListCart.add(
                    CartModel(
                        pId = pId,
                        vId = vId,
                        itemName = itemName,
                        discountedPrice = discountedPrice.toString(),
                        actualPrice = actualPrice,
                        color = selectedColor,
                        size = selectedSize,
                        quantity = currentNumber,
                        totalAmount = totalAmount,
                        imageUrl = imageUrl,
                        stock = stock,
                        variantsArr = arrayListVariant,
                        variants = variantArray
                    )
                )
                DashBoardActivity.selectedVIDs.add(vId)
                Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show()

                //store  arraylist in SP
                sharedPreference.setArray()
            }

//            DashBoardActivity.arrayListVariants.addAll(variantArray)
//            sharedPreference.setArray()

            /* if (DashBoardActivity.selectedVIDs.contains(vId)) {
                     if (product.variants.contains(Variants(color = color, size = size, quantity = quantitySze))) {
                         Toast.makeText(this, "Product already in cart", Toast.LENGTH_SHORT).show()
                     }else{
                         product.variants.add(Variants(color = color, size = size, quantity = quantitySze))
                         DashBoardActivity.arrayListCart.add(
                             product
                         )
                     }

             }
             else {

                 product.variants.add(Variants(color = color, size = size, quantity = quantitySze))
                 DashBoardActivity.arrayListCart.add(
                     product
                 )
                 DashBoardActivity.selectedVIDs.add(vId)
                 Toast.makeText(this, "Product Added Successfully", Toast.LENGTH_SHORT).show()

                 //store  arraylist in SP
                 sharedPreference.setArray()
             }*/

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
            @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
            override fun onResponse(
                call: Call<ProductResponse>, response: Response<ProductResponse>
            ) {
                if (response.isSuccessful) {
                    val productResponse = response.body()
                    if (productResponse!!.status) {

                        pId = productResponse.product._id
                        itemName = productResponse.product.productName
                        imageUrl = productResponse.product.productImages[0]
                        binding.idTV.text = "#Id -${productResponse.product.productId}"
                        binding.tShirtNameTV.text = productResponse.product.productName

                        binding.actualPriceTV.text = "Rs ${productResponse.product.price}.00"
                        var discount = 0
                        if (productResponse.product.discountType == 1) {
                            binding.discountTV.text = "${productResponse.product.discount} % OFF"
                            discount =
                                (productResponse.product.price * productResponse.product.discount) / 100

                            binding.discountedPriceTV.text =
                                "Rs ${productResponse.product.price - discount}.00"
                        } else if (productResponse.product.discountType == 0) {
                            binding.discountTV.text = "Rs ${productResponse.product.discount} OFF"
                            discount =
                                (productResponse.product.price - productResponse.product.discount)
                            binding.discountedPriceTV.text = "Rs ${discount}.00"

                        } else {
                            binding.discountTV.visibility = View.GONE
                            binding.discountTagRightIV.visibility = View.GONE
                        }

//                        if (productResponse.product.discountType == 1) {
//                            binding.discountTV.text =
//                                "${productResponse.product.discount} % OFF"
//                        }

                        if (productResponse.product.stock <= 50) {
                            binding.unitesLeftTV.visibility = View.VISIBLE
//                            binding.unitesLeftTV.text = "Few units left ${productResponse.product.stock}"
                        } else {
                            binding.unitesLeftTV.visibility = View.GONE
                        }

                        val arrayBanner: ArrayList<String> = ArrayList()
                        arrayBanner.addAll(productResponse.product.productImages)
                        productBannerAdapter = ProductBannerAdapter(
                            this@ProductDetailsActivity, arrayBanner
                        )
                        binding.productViewPager.adapter = productBannerAdapter
                        setupIndicators()
                        setCurrentIndicator(0)

                        binding.downloadImageIV.setOnClickListener {

                            downloadFile(productResponse.product.productImages[0])

                        }

//                        if (arrayBanner.isNotEmpty()) autoSlide(arrayBanner.size)

//                        binding.dispatchedTV.text = productResponse.product.dispatchDetails[0]
//                        if (productResponse.product.dispatchDetails[1].isNotEmpty()) {
                        binding.dispatchedTV.text = productResponse.product.deliveryInstructions[0]
                        binding.deliveryTV.text = productResponse.product.dispatchDetails[0]
//                        }
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
                                    productResponse.product.description, Html.FROM_HTML_MODE_COMPACT
                                )
                            } else {
                                Html.fromHtml(productResponse.product.description)
                            }
//                        binding.productCheckListDetailsTV.text =
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                                Html.fromHtml(
//                                    " ${productResponse.product.productCheckList.box} ${productResponse.product.productCheckList.color}",
//                                    Html.FROM_HTML_MODE_COMPACT
//                                )
//                            } else {
//                                Html.fromHtml(productResponse.product.productCheckList.toString())
//                            }

                        binding.productCheckListDetailsTV.text =
                            "Item : ${productResponse.product.productCheckList.box} \nColor : ${productResponse.product.productCheckList.color}"

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
                            this@ProductDetailsActivity, arrayListSimilarProduct
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

                        dType = productResponse.product.discountType
                        dPrice = productResponse.product.price
                        dDiscount = productResponse.product.discount

                        Log.e("TAG", "${response.message()} ")
                    }
                } else {

                    val jObjError = JSONObject(response.errorBody()!!.string())
                    Toast.makeText(
                        this@ProductDetailsActivity,
                        jObjError.getString("message"),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ProductResponse>, t: Throwable) {
                Toast.makeText(
                    this@ProductDetailsActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("TAG", "${t.message} ")
            }
        })
    }

    fun colorSizeUpdate() {

        colorSize
        selectedColor
        totalAmount = discountedPrice * currentNumber
        variantTarget = "${selectedColor}-${selectedSize}"
        Log.d("variantTarget", variantTarget)
        Log.d("colorSelect1", selectedColor)
        updatePrice()

    }

    fun sizeUpdate() {

        sizeOfSize
        selectedSize
        totalAmount = discountedPrice * currentNumber
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
        binding.addToCartTV.setBackgroundResource(R.drawable.button_grey_radius5)

    }

    fun activeAddCart() {

        if (colorSize == 1 && sizeOfSize == 1 && quantitySze == 1) {
            binding.addToCartTV.isEnabled = true
            binding.buyNowTV.isEnabled = true
            binding.addToCartTV.setBackgroundResource(R.drawable.button_blue_radius5)

        }
    }

    @SuppressLint("SetTextI18n")
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

//                    unitPrice = elements.price
//                    binding.discountedPriceTV.text = "Rs ${elements.price}.00"

                    var discount = 0
                    Log.d("unitPrice.", elements.discountType.toString())
                    if (dType == 1) {

                        discount = (elements.price * dDiscount) / 100
                        unitPrice = elements.price - discount
                        discountedPrice = unitPrice
                        actualPrice = unitPrice
                        binding.discountedPriceTV.text = "Rs ${elements.price - discount}.00"

                        Log.d("unitPrice.", unitPrice.toString())
                        Log.d("unitPrice.", "${(dPrice)}  ${(dDiscount)}")

                    } else if (dType == 0) {

                        discount = (elements.price - dDiscount)
                        unitPrice = elements.price - dDiscount
                        binding.discountedPriceTV.text = "Rs ${discount}.00"
                        discountedPrice = unitPrice
                        actualPrice = unitPrice

                        Log.d("unitPrice..", "${(dPrice)} - ${(dDiscount)}")
                    }

//                    discountedPrice = elements.price
                    binding.actualPriceTV.text = "Rs ${elements.actualPrice}.00"
//                    actualPrice = elements.actualPrice
//                    totalAmount = arrayListVariant.sumBy { it.price }
                    stock = elements.stock
                    Log.d("Stock", stock.toString())
                }

            }
        }
    }

    fun updateCurrentNumber() {

        if (colorSize == 1 && sizeOfSize == 1) {
            binding.addToCartTV.isEnabled = true
            binding.buyNowTV.isEnabled = true
            binding.addToCartTV.setBackgroundResource(R.drawable.button_blue_radius5)

            currentNumber = 1
            binding.quantityShowTV.text = currentNumber.toString()

        } else {
            binding.addToCartTV.isEnabled = false
            binding.buyNowTV.isEnabled = false

            currentNumber = 0
            binding.quantityShowTV.text = currentNumber.toString()
        }

    }

    fun setArray() {
        sharedPreference.setArray()
    }

    fun downloadFile(url: String) {
        Toast.makeText(this, "Image downloading...", Toast.LENGTH_SHORT).show()
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // this will request for permission when user has not granted permission for the app

            ActivityCompat.requestPermissions(
                this as Activity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        } else {

            //Download Script

            val downloadManager =
                this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager?
            val uri = Uri.parse(url)
            val request = DownloadManager.Request(uri)
            request.setVisibleInDownloadsUi(true)
            request.setTitle(this.resources.getString(R.string.app_name))
            request.setDescription("Downloading...")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
//                uri.lastPathSegment
                this.resources.getString(R.string.app_name) + "${imageNumber}.jpg"
            )
            imageNumber++
            downloadManager!!.enqueue(request)
        }
    }
}


