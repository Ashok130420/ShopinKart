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
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.classes.SharedPreference
import com.example.shopinkarts.databinding.ActivityPersonalDetailsBinding

class PersonalDetailsActivity : AppCompatActivity() {
    lateinit var sharedPreference: SharedPreference
    lateinit var binding: ActivityPersonalDetailsBinding
    var layoutCount = 1
    var userType = ""
    var state = ""
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
        binding.includeStepper1.stateSpinner
        binding.includeStepper1.landMarkET.setText(sharedPreference.getLandmark())

        binding.includeStepper1.stateSpinner.onItemSelectedListener =
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
                    }

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }



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
                state = binding.includeStepper1.stateSpinner.toString(),
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
            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
        }
        binding.includeStepper2.payOnlineCL.setOnClickListener {
            binding.includeStepper2.onlineSelectIV.setBackgroundResource(R.drawable.green_right_icon)
            binding.includeStepper2.caseSelectIV.setBackgroundResource(R.drawable.grey_right_icon)
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
        layoutCount++

    }

    private fun layoutSecond() {
        binding.includeStepper1.personalCL.visibility = View.GONE
        binding.includeStepper1.businessDetailsCl.visibility = View.GONE
        binding.includeStepper2.paymentCL.visibility = View.VISIBLE
        binding.includeStepper3.successCL.visibility = View.GONE
        binding.paymentIV.setImageResource(R.drawable.blue_right_icon)
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
}