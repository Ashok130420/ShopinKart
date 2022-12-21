package com.app.shopinkarts.activity

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.api.RetrofitClient
import com.app.shopinkarts.classes.SharedPreference
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityCheckoutDetailsBinding
import com.app.shopinkarts.model.CreateOrderRequest
import com.app.shopinkarts.model.CreateProduct
import com.app.shopinkarts.model.ShippingDetails
import com.app.shopinkarts.response.SuccessResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckOutDetailsActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    lateinit var binding: ActivityCheckoutDetailsBinding
    var layoutCount = 1
    var userType = ""

    var totalAmount: Double = 0.00
    var gst: Double = 0.00
    var discountAmount: Double = 0.00
    var amountPaid: Double = 0.00
    var paymentType = 0

    var name = ""
    var nameBusiness = ""
    var phone = ""
    var phoneBusiness = ""
    var flat = ""
    var flatBusiness = ""
    var street = ""
    var streetBusiness = ""
    var pin = ""
    var pinBusiness = ""
    var city = ""
    var cityBusiness = ""
    var state = ""
    var stateBusiness = ""
    var landmark = ""
    var landmarkBusiness = ""
    var companyBusiness = ""
    var gstBusiness = ""
    var opertingStateBusiness = ""
    var deliveryInstruction = ""


    var shippingName = ""
    var shippingPhone = ""
    var shippingHouseNo = ""
    var shippingStreet = ""
    var shippingPinCode = ""
    var shippingCity = ""
    var shippingState = ""
    var shippingLandMark = ""


    var totalPrice = 0
    var finalPrice = 0
    var totalQuantity = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this, R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout_details)

        layoutCount = 1

//        binding.includeStepper1.personalCL.visibility = View.GONE
//        binding.includeStepper1.businessDetailsCl.visibility = View.GONE


        layoutFirst()

        sharedPreference = SharedPreference(this)

        userType = sharedPreference.getUserType().toString()
        Log.d("USERTYPE.....", userType)

        if (userType == "0") {
            binding.includeStepper1.businessDetailsCl.visibility = View.GONE
            binding.includeStepper1.personalCL.visibility = View.VISIBLE

            binding.includeStepper1.nameET.setText(sharedPreference.getName())
            binding.includeStepper1.phoneNumberET.setText(sharedPreference.getPhone())
            binding.includeStepper1.phoneNumberET.setText(sharedPreference.getPhoneNo())
            binding.includeStepper1.flatHouseET.setText(sharedPreference.getFlat())
            binding.includeStepper1.streetET.setText(sharedPreference.getStreet())
            binding.includeStepper1.pinCodeET.setText(sharedPreference.getPin())
            binding.includeStepper1.cityET.setText(sharedPreference.getCity())
//      binding.includeStepper1.stateSpinner.setSelection(state.toInt())
            state = sharedPreference.getState().toString()
            Log.d("state", "onItemSelected:  $state ")
            binding.includeStepper1.landMarkET.setText(sharedPreference.getLandmark())

        } else if (userType == "1") {
            binding.includeStepper1.personalCL.visibility = View.GONE
            binding.includeStepper1.businessDetailsCl.visibility = View.VISIBLE


            binding.includeStepper1.businessNameET.setText(sharedPreference.getBusinessName())
            binding.includeStepper1.firmNameET.setText(sharedPreference.getBusinessCompany())
            binding.includeStepper1.gstInET.setText(sharedPreference.getGst())
            binding.includeStepper1.gstInET.setText(sharedPreference.getBusinessGst())
            binding.includeStepper1.businessPhoneNumberET.setText(sharedPreference.getBusinessPhoneNo())
            binding.includeStepper1.businessPhoneNumberET.setText(sharedPreference.getPhoneNo())
            binding.includeStepper1.businessFlatHouseET.setText(sharedPreference.getBusinessFlat())
            binding.includeStepper1.businessStreetET.setText(sharedPreference.getBusinessStreet())
            binding.includeStepper1.businessPinCodeET.setText(sharedPreference.getBusinessPin())
            binding.includeStepper1.businessCityET.setText(sharedPreference.getBusinessCity())
            binding.includeStepper1.businessLandMarkET.setText(sharedPreference.getBusinessLandmark())

        }

        binding.includeStepper1.stateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, positionState: Int, id: Long
                ) {

                    val selectedItem = parent?.getItemAtPosition(positionState).toString()
                    if (positionState == 0) {
                        state = ""
                    } else {
                        state = selectedItem
                        Log.d("state_state", "onItemSelected: $state")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        binding.includeStepper1.businessStateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, positionBusinessState: Int, id: Long
                ) {

                    val selectedItem = parent?.getItemAtPosition(positionBusinessState).toString()
                    if (positionBusinessState == 0) {
                        stateBusiness = ""
                    } else {
                        stateBusiness = selectedItem
                        Log.d("state_state", "onItemSelected: $stateBusiness")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        binding.includeStepper1.businessOperatingStateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    positionBusinessOperateState: Int,
                    id: Long
                ) {

                    val selectedItem =
                        parent?.getItemAtPosition(positionBusinessOperateState).toString()
                    if (positionBusinessOperateState == 0) {
                        opertingStateBusiness = ""
                    } else {
                        opertingStateBusiness = selectedItem
                        Log.d("state_state", "onItemSelected: $opertingStateBusiness")
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }


        binding.shippingCL.setOnClickListener {
            layoutFirst()
            if (userType == "0") {
                binding.includeStepper1.businessDetailsCl.visibility = View.GONE
                binding.includeStepper1.personalCL.visibility = View.VISIBLE
            } else if (userType == "1") {
                binding.includeStepper1.personalCL.visibility = View.GONE
                binding.includeStepper1.businessDetailsCl.visibility = View.VISIBLE
            }
        }
        binding.paymentCL.setOnClickListener {
//            layoutSecond()
        }
        binding.successCL.setOnClickListener {
//            layoutThird()
        }

        totalAmount = intent.extras!!.getDouble("totalAmount", 0.0)
        gst = intent.extras!!.getDouble("gst", 0.0)
        discountAmount = intent.extras!!.getDouble("discount", 0.0)
        amountPaid = intent.extras!!.getDouble("finalAmount", 0.0)


        Log.d("TAG1", "onCreate: $totalAmount")
        Log.d("TAG2", "onCreate: $gst")
        Log.d("TAG3", "onCreate: $discountAmount")
        Log.d("TAG4", "onCreate: $amountPaid")


        binding.headerPersonalDetails.nameTV.text = resources.getString(R.string.personal_details)

        binding.headerPersonalDetails.backIV.setOnClickListener {
            onBackPressed()
        }

        binding.continueTV.setOnClickListener {
            if (userType == "0") {
                deliveryInstruction = binding.includeStepper1.deliveryInstructionsET.text.toString()
            } else {
                deliveryInstruction =
                    binding.includeStepper1.deliveryInstructionsBusinessET.text.toString()
            }

            name = binding.includeStepper1.nameET.text.toString().trim()
            phone = binding.includeStepper1.phoneNumberET.text.toString().trim()
            flat = binding.includeStepper1.flatHouseET.text.toString().trim()
            street = binding.includeStepper1.streetET.text.toString().trim()
            pin = binding.includeStepper1.pinCodeET.text.toString().trim()
            city = binding.includeStepper1.cityET.text.toString().trim()
            state = state
            landmark = binding.includeStepper1.landMarkET.text.toString().trim()

            nameBusiness = binding.includeStepper1.businessNameET.text.toString().trim()
            companyBusiness = binding.includeStepper1.firmNameET.text.toString().trim()
            gstBusiness = binding.includeStepper1.gstInET.text.toString().trim()
            phoneBusiness = binding.includeStepper1.businessPhoneNumberET.text.toString().trim()
            flatBusiness = binding.includeStepper1.businessFlatHouseET.text.toString().trim()
            streetBusiness = binding.includeStepper1.businessStreetET.text.toString().trim()
            pinBusiness = binding.includeStepper1.businessPinCodeET.text.toString().trim()
            cityBusiness = binding.includeStepper1.businessCityET.text.toString().trim()



            if (userType == "0") {
                if (name.isEmpty()) {
                    binding.includeStepper1.nameET.error = "Enter name"
                    binding.includeStepper1.nameET.requestFocus()
                    return@setOnClickListener
                }
                if (phone.isEmpty()) {
                    binding.includeStepper1.phoneNumberET.error = "Enter phone number"
                    binding.includeStepper1.phoneNumberET.requestFocus()
                    return@setOnClickListener
                }
                if (flat.isEmpty()) {
                    binding.includeStepper1.flatHouseET.error = "Enter house no"
                    binding.includeStepper1.flatHouseET.requestFocus()
                    return@setOnClickListener
                }
                if (street.isEmpty()) {
                    binding.includeStepper1.streetET.error = "Enter street"
                    binding.includeStepper1.streetET.requestFocus()
                    return@setOnClickListener
                }
                if (pin.isEmpty()) {
                    binding.includeStepper1.pinCodeET.error = "Enter pin code"
                    binding.includeStepper1.pinCodeET.requestFocus()
                    return@setOnClickListener
                }
                if (city.isEmpty()) {
                    binding.includeStepper1.cityET.error = "Enter city"
                    binding.includeStepper1.cityET.requestFocus()
                    return@setOnClickListener
                }

                sharedPreference.setAddress(
                    name = binding.includeStepper1.nameET.text.toString(),
                    phone = binding.includeStepper1.phoneNumberET.text.toString(),
                    flat = binding.includeStepper1.flatHouseET.text.toString(),
                    street = binding.includeStepper1.streetET.text.toString(),
                    pin = binding.includeStepper1.pinCodeET.text.toString(),
                    city = binding.includeStepper1.cityET.text.toString(),
                    state = state,
                    landmark = binding.includeStepper1.landMarkET.text.toString()
                )
            } else if (userType == "1") {

                if (nameBusiness.isEmpty()) {
                    binding.includeStepper1.businessNameET.error = "Enter name.."
                    binding.includeStepper1.businessNameET.requestFocus()
                    return@setOnClickListener
                }
                if (companyBusiness.isEmpty()) {
                    binding.includeStepper1.firmNameET.error = "Enter company name"
                    binding.includeStepper1.firmNameET.requestFocus()
                    return@setOnClickListener
                }
                if (gstBusiness.isEmpty()) {
                    binding.includeStepper1.gstInET.error = "Enter gst no"
                    binding.includeStepper1.gstInET.requestFocus()
                    return@setOnClickListener
                }
                if (phoneBusiness.isEmpty()) {
                    binding.includeStepper1.businessPhoneNumberET.error = "Enter phone no"
                    binding.includeStepper1.businessPhoneNumberET.requestFocus()
                    return@setOnClickListener
                }
                if (flatBusiness.isEmpty()) {
                    binding.includeStepper1.businessFlatHouseET.error = "Enter flat house no"
                    binding.includeStepper1.businessFlatHouseET.requestFocus()
                    return@setOnClickListener
                }
                if (streetBusiness.isEmpty()) {
                    binding.includeStepper1.businessStreetET.error = "Enter street"
                    binding.includeStepper1.businessStreetET.requestFocus()
                    return@setOnClickListener
                }
                if (pinBusiness.isEmpty()) {
                    binding.includeStepper1.businessPinCodeET.error = "Enter pin code"
                    binding.includeStepper1.businessPinCodeET.requestFocus()
                    return@setOnClickListener
                }
                if (cityBusiness.isEmpty()) {
                    binding.includeStepper1.businessCityET.error = "Enter cty"
                    binding.includeStepper1.businessCityET.requestFocus()
                    return@setOnClickListener
                }

                sharedPreference.setBusinessAddress(
                    bname = binding.includeStepper1.businessNameET.text.toString(),
                    bphone = binding.includeStepper1.businessPhoneNumberET.text.toString(),
                    bflat = binding.includeStepper1.businessFlatHouseET.text.toString(),
                    bstreet = binding.includeStepper1.businessStreetET.text.toString(),
                    bpin = binding.includeStepper1.businessPinCodeET.text.toString(),
                    bcity = binding.includeStepper1.businessCityET.text.toString(),
                    bstate = stateBusiness,
                    blandmark = binding.includeStepper1.businessLandMarkET.text.toString(),
                    bcompany = binding.includeStepper1.firmNameET.text.toString(),
                    bgst = binding.includeStepper1.gstInET.text.toString(),
                )
            }


            if (layoutCount < 4) {
                preNextFunction()
            } else {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
            }

        }

        val ss = SpannableString(
            "You can track the delivery in\nthe " + "\"Order\" section"
        )
        val span1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {

                val intent = Intent(this@CheckOutDetailsActivity, TrackOrderActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@CheckOutDetailsActivity, "clicked", Toast.LENGTH_SHORT).show()
            }
        }

        ss.setSpan(span1, 35, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.includeStepper3.trackDeliveryTV.text = ss
        binding.includeStepper3.trackDeliveryTV.highlightColor =
            ContextCompat.getColor(this, R.color.primary_Blue)
        binding.includeStepper3.trackDeliveryTV.movementMethod = LinkMovementMethod.getInstance()


        binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.green_right_icon)
//        binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)

        binding.includeStepper2.cashOnDeliveryCL.setOnClickListener {
            paymentType = 0
            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.green_right_icon)
//            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
            Log.d("paymentType", "onCreate: $paymentType")
        }
        binding.includeStepper2.payOnlineCL.setOnClickListener {
//            paymentType = 1
//            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.green_right_icon)
//            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
            Toast.makeText(this, "Pay online coming soon...", Toast.LENGTH_SHORT).show()
            Log.d("paymentType", "onCreate: $paymentType")
        }

        /*  binding.continueTV.setOnClickListener {
              val intent = Intent(this, DashBoardActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
              startActivity(intent)
          }*/

        binding.goToOrdersTV.setOnClickListener {

            val intent = Intent(this, DashBoardActivity::class.java)
            intent.putExtra("from", "myOrder")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

    }

    // To remove EditText focus on touch outside
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun preNextFunction() {
        if (layoutCount >= 3) {
//            binding.continueTV.text = resources.getString(R.string.continue_shopping)
//            binding.goToOrdersTV.visibility = View.VISIBLE

        } else {
            binding.continueTV.text = resources.getString(R.string.continue_)
        }

        Log.d("TAG", "preNextFunction: $layoutCount")

        when (layoutCount) {
            2 -> {
                layoutSecond()

                Log.d("layoutCount", layoutCount.toString())
            }
            3 -> {
//                layoutThird()

                orderApi()


            }
            else -> {
                val intent = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    private fun layoutFirst() {
//        if (userType == "0") {
//            binding.includeStepper1.businessDetailsCl.visibility = View.GONE
//            binding.includeStepper1.personalCL.visibility = View.VISIBLE
//        } else if (userType == "1") {
//            binding.includeStepper1.personalCL.visibility = View.GONE
//            binding.includeStepper1.businessDetailsCl.visibility = View.VISIBLE
//        }
        binding.includeStepper2.paymentCL.visibility = View.GONE
        binding.includeStepper3.successCL.visibility = View.GONE
        binding.paymentIV.setImageResource(R.drawable.grey_right_icon)
        binding.successIV.setImageResource(R.drawable.grey_right_icon)
        layoutCount++


    }

    private fun layoutSecond() {
        binding.includeStepper1.personalCL.visibility = View.GONE
        binding.includeStepper1.businessDetailsCl.visibility = View.GONE
        binding.includeStepper2.paymentCL.visibility = View.VISIBLE
        binding.includeStepper3.successCL.visibility = View.GONE
        binding.paymentIV.setImageResource(R.drawable.blue_right_icon)
        binding.successIV.setImageResource(R.drawable.grey_right_icon)

        layoutCount++
    }

    private fun layoutThird() {
        binding.includeStepper1.personalCL.visibility = View.GONE
        binding.includeStepper1.businessDetailsCl.visibility = View.GONE
        binding.includeStepper2.paymentCL.visibility = View.GONE
        binding.includeStepper3.successCL.visibility = View.VISIBLE
        binding.successIV.setImageResource(R.drawable.blue_right_icon)
        layoutCount++
    }

    private fun orderApi() {

        val arrayProduct: ArrayList<CreateProduct> = ArrayList()


        for (item in DashBoardActivity.arrayListCart) {
//        totalAmount = DashBoardActivity.arrayListCart.sumOf { it.totalAmount }.toDouble()

            var t = 0
            var q = 0
            for (i in item.variants) {

                finalPrice += i.price * i.quantity
                t += i.price * i.quantity
                Log.d("totalPrice", totalPrice.toString())

                q += i.quantity
                Log.d("totalQuantity", q.toString())
            }

            totalQuantity = q
            totalPrice = t
//            val sum= item.variants.sumOf { it.price }

            arrayProduct.add(
                CreateProduct(
                    productId = item.pId,
                    productImage = item.imageUrl,
                    productName = item.itemName,
                    qty = totalQuantity,
                    totalAmount = totalPrice.toDouble(),
                    variants = item.variants,
                    pId = item.productId,
                    hsnCode = item.hsnCode
                )
            )
            Log.d("arrayProduct",item.pId)
            Log.d("arrayProduct",item.vId)
            Log.d("arrayProduct", arrayProduct.toString())

        }

        if (userType == "0") {
            shippingName = binding.includeStepper1.nameET.text.toString()
            shippingPhone = binding.includeStepper1.phoneNumberET.text.toString()
            shippingHouseNo = binding.includeStepper1.flatHouseET.text.toString()
            shippingStreet = binding.includeStepper1.streetET.text.toString()
            shippingPinCode = binding.includeStepper1.pinCodeET.text.toString()
            shippingCity = binding.includeStepper1.cityET.text.toString()
            shippingState = state
            shippingLandMark = binding.includeStepper1.landMarkET.text.toString()
        } else {
            shippingName = binding.includeStepper1.businessNameET.text.toString()
            shippingPhone = binding.includeStepper1.businessPhoneNumberET.text.toString()
            shippingHouseNo = binding.includeStepper1.businessFlatHouseET.text.toString()
            shippingStreet = binding.includeStepper1.businessStreetET.text.toString()
            shippingPinCode = binding.includeStepper1.businessPinCodeET.text.toString()
            shippingCity = binding.includeStepper1.businessCityET.text.toString()
            shippingState = stateBusiness
            shippingLandMark = binding.includeStepper1.businessLandMarkET.text.toString()
        }

        val shippingDetails = ShippingDetails(
            name = shippingName,
            phone = shippingPhone,
            houseNo = shippingHouseNo,
            street = shippingStreet,
            pincode = shippingPinCode,
            city = shippingCity,
            state = shippingState,
            landmark = shippingLandMark
        )

        val requestBody = CreateOrderRequest(

            discount = discountAmount.toString(),
            finalAmount = (finalPrice + gst).toString(),
            gstAmount = gst.toString(),
            paymentType = paymentType.toString(),
            products = arrayProduct,
            shippingDetails = shippingDetails,
            totalAmount = finalPrice.toString(),
            userId = sharedPreference.getUserId().toString(),
            businessOperatingState = opertingStateBusiness,
            companyName = companyBusiness,
            deliveryInstruction = deliveryInstruction,
            gstIn = gstBusiness


        )
        Log.d("requestBody", "orderApi: $requestBody")


        val call: Call<SuccessResponse> = RetrofitClient.instance!!.api.ordersApi(requestBody)
        call.enqueue(object : Callback<SuccessResponse> {
            override fun onResponse(
                call: Call<SuccessResponse>, response: Response<SuccessResponse>
            ) {
                if (response.isSuccessful) {
                    val orderResponse = response.body()
                    if (orderResponse!!.status) {

                        DashBoardActivity.arrayListCart.clear()
                        DashBoardActivity.selectedVIDs.clear()
                        sharedPreference.setArray()

//                        Toast.makeText(
//                            this@CheckOutDetailsActivity, response.message(), Toast.LENGTH_SHORT
//                        ).show()
                        Log.e("orderResponse", "${response.message()} ")

                        layoutThird()
                        binding.continueTV.text = resources.getString(R.string.continue_shopping)
                        binding.goToOrdersTV.visibility = View.VISIBLE

                    } else {

                        val jObjError = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(
                            this@CheckOutDetailsActivity,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()

                        Log.e("orderResponse", "${response.message()} ")

                    }
                    DashBoardActivity.arrayListCart.clear()
                    DashBoardActivity.selectedVIDs.clear()
                    sharedPreference.setArray()
                }
            }

            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Toast.makeText(
                    this@CheckOutDetailsActivity, "${t.message}", Toast.LENGTH_SHORT
                ).show()
                Log.e("orderResponse", "${t.message} ")
            }
        })
    }


}




