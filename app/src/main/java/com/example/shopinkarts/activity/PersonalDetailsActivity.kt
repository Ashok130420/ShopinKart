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
import com.example.shopinkarts.databinding.ActivityPersonalDetailsBinding

class PersonalDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityPersonalDetailsBinding
    var layoutCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_details)

        layoutCount = 1
        layoutFirst()

        binding.headerPersonalDetails.nameTV.text = resources.getString(R.string.personal_details)
        binding.headerPersonalDetails.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.continueTV.setOnClickListener {

            if (layoutCount < 4) {
                preNextFunction()
            } else {
                val intent = Intent(this, DashBoardActivity::class.java)
                startActivity(intent)
            }

        }
        val ss = SpannableString(
            "You can track the delivery in the \n" +
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

        if (layoutCount == 2) {
            layoutSecond()

            Log.d("layoutCount", layoutCount.toString())
        } else if (layoutCount == 3) {
            layoutThird()

        } else {
            val intent = Intent(this, DashBoardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun layoutFirst() {
        binding.includeStepper1.personalCL.visibility = View.VISIBLE
        binding.includeStepper2.paymentCL.visibility = View.GONE
        binding.includeStepper3.successCL.visibility = View.GONE
        layoutCount++

    }

    private fun layoutSecond() {
        binding.includeStepper1.personalCL.visibility = View.GONE
        binding.includeStepper2.paymentCL.visibility = View.VISIBLE
        binding.includeStepper3.successCL.visibility = View.GONE
        binding.paymentIV.setImageResource(R.drawable.blue_right_icon)
        layoutCount++
    }

    private fun layoutThird() {
        binding.includeStepper1.personalCL.visibility = View.GONE
        binding.includeStepper2.paymentCL.visibility = View.GONE
        binding.includeStepper3.successCL.visibility = View.VISIBLE
        binding.successIV.setImageResource(R.drawable.blue_right_icon)
        layoutCount++
    }
}