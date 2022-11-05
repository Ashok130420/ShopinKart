package com.app.shopinkarts.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.shopinkarts.R
import com.app.shopinkarts.adapter.ReviewsAdapter
import com.app.shopinkarts.classes.Utils
import com.app.shopinkarts.databinding.ActivityReadReviewsBinding

class ReadReviewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityReadReviewsBinding
    lateinit var reviewsAdapter: ReviewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.changeStatusTextColor(this)
        Utils.changeStatusColor(this,R.color.white)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_read_reviews)

        binding.headerReadReview.backIV.setOnClickListener {
            onBackPressed()
        }
        binding.headerReadReview.nameTV.text =  resources.getString(R.string.read_reviews)

        // adapter for reviews
        reviewsAdapter = ReviewsAdapter(this)
        binding.reviewsRV.adapter = reviewsAdapter
        binding.reviewsRV.isNestedScrollingEnabled = false

    }
}