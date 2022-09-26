package com.example.shopinkarts.activity

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
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.api.RetrofitClient
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ActivityPersonalDetailsBinding
import com.example.shopinkarts.model.CreateOrderRequest
import com.example.shopinkarts.model.CreateProduct
import com.example.shopinkarts.model.ShippingDetails
import com.example.shopinkarts.response.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonalDetailsActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    lateinit var binding: ActivityPersonalDetailsBinding
    var layoutCount = 1
    var userType = ""
    var state = ""

    var totalAmount: Double = 0.00
    var gst = 0F
    var discountAmount = 0F
    var amountPaid = 0F
    var paymentType = 0

    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_details)

        layoutCount = 1
        layoutFirst()


        sharedPreference = SharedPreference(this)
        userType = sharedPreference.getUserType().toString()
        Log.d("USERTYPE.....", userType)

        binding.includeStepper1.nameET.setText(sharedPreference.getName())
        binding.includeStepper1.phoneNumberET.setText(sharedPreference.getPhone())
        binding.includeStepper1.flatHouseET.setText(sharedPreference.getFlat())
        binding.includeStepper1.streetET.setText(sharedPreference.getStreet())
        binding.includeStepper1.pinCodeET.setText(sharedPreference.getPin())
        binding.includeStepper1.cityET.setText(sharedPreference.getCity())
//      binding.includeStepper1.stateSpinner.setSelection(state.toInt())
        state = sharedPreference.getState().toString()
        Log.d("state", "onItemSelected:  $state ")
        binding.includeStepper1.landMarkET.setText(sharedPreference.getLandmark())

        /*        binding.includeStepper1.stateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    positionState: Int,
                    id: Long
                ) {

                    val selectedItem = parent?.getItemAtPosition(positionState).toString()
                    if (positionState == 0) {
                        state = ""
                    } else {
                        state = selectedItem
//                        Log.d("state", "onItemSelected:  $state ")
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }*/

        binding.shippingCL.setOnClickListener {
            layoutFirst()
        }
        binding.paymentCL.setOnClickListener {
            layoutSecond()
        }
        binding.successCL.setOnClickListener {
//            layoutThird()
        }

        totalAmount = intent.extras!!.getDouble("totalAmount", 0.0)
        gst = intent.extras!!.getFloat("gst", 0F)
        discountAmount = intent.extras!!.getFloat("discount", 0F)
        amountPaid = intent.extras!!.getFloat("finalAmount", 0F)


        Log.d("TAG1", "onCreate: $totalAmount")
        Log.d("TAG2", "onCreate: $gst")
        Log.d("TAG3", "onCreate: $discountAmount")
        Log.d("TAG4", "onCreate: $amountPaid")


        binding.headerPersonalDetails.nameTV.text = resources.getString(R.string.personal_details)
        binding.headerPersonalDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.continueTV.setOnClickListener {
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
            if (layoutCount < 4) {
                preNextFunction()
            } else {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
            }

        }
        val ss = SpannableString(
            "You can track the delivery in\nthe " +
                    "\"Order\" section"
        )
        val span1: ClickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {

                val intent = Intent(this@PersonalDetailsActivity, TrackOrderActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@PersonalDetailsActivity, "clicked", Toast.LENGTH_SHORT).show()
            }
        }

        ss.setSpan(span1, 36, 41, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.includeStepper3.trackDeliveryTV.text = ss
        binding.includeStepper3.trackDeliveryTV.highlightColor =
            ContextCompat.getColor(this, R.color.primary_Blue)
        binding.includeStepper3.trackDeliveryTV.movementMethod = LinkMovementMethod.getInstance()


        binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
        binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)

        binding.includeStepper2.cashOnDeliveryCL.setOnClickListener {
            paymentType = 0
            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
            Log.d("paymentType", "onCreate: $paymentType")
        }
        binding.includeStepper2.payOnlineCL.setOnClickListener {
            paymentType = 1
            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
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
            binding.continueTV.text = resources.getString(R.string.continue_shopping)
            binding.goToOrdersTV.visibility = View.VISIBLE

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
                layoutThird()
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
        if (userType == "1") {
            binding.includeStepper1.businessDetailsCl.visibility = View.VISIBLE
        } else {
            binding.includeStepper1.personalCL.visibility = View.VISIBLE
        }
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
            arrayProduct.add(
                CreateProduct(
                    productId = item.pId,
                    productImage = item.imageUrl,
                    productName = item.itemName,
                    qty = item.quantity,
                    totalAmount = item.totalAmount.toDouble(),
                    variantId = item.vId
                )
            )
        }

        val shippingDetails = ShippingDetails(
            name = binding.includeStepper1.nameET.text.toString(),
            phone = binding.includeStepper1.phoneNumberET.text.toString(),
            houseNo = binding.includeStepper1.flatHouseET.text.toString(),
            street = binding.includeStepper1.streetET.text.toString(),
            pincode = binding.includeStepper1.pinCodeET.text.toString(),
            city = binding.includeStepper1.cityET.text.toString(),
            state = state,
            landmark = binding.includeStepper1.landMarkET.text.toString()
        )
//        val requestBody = OrdersResponse(
        val requestBody = CreateOrderRequest(

            discount = discountAmount.toString(),
            finalAmount = amountPaid.toString(),
            gstAmount = gst.toString(),
            paymentType = paymentType.toString(),
            products = arrayProduct,
            shippingDetails = shippingDetails,
            totalAmount = totalAmount.toString(),
            userId = sharedPreference.getUserId().toString(),

        )
//        )

        val call: Call<SuccessResponse> = RetrofitClient.instance!!.api.ordersApi(requestBody)
        call.enqueue(object : Callback<SuccessResponse> {
            override fun onResponse(
                call: Call<SuccessResponse>,
                response: Response<SuccessResponse>
            ) {
                if (response.isSuccessful) {
                    val orderResponse = response.body()
                    if (orderResponse!!.status) {

                        Toast.makeText(
                            this@PersonalDetailsActivity, response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("orderResponse", "${response.message()} ")

                    } else {
                        Toast.makeText(
                            this@PersonalDetailsActivity, response.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("orderResponse", "${response.message()} ")

                    }
                }
            }

            override fun onFailure(call: Call<SuccessResponse>, t: Throwable) {
                Toast.makeText(
                    this@PersonalDetailsActivity, "${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("orderResponse", "${t.message} ")
            }
        })
    }
}




