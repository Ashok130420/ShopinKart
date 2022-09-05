package com.example.shopinkarts.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.shopinkarts.R
import com.example.shopinkarts.adapter.ReviewsAdapter
import com.example.shopinkarts.databinding.ActivityReadReviewsBinding

class ReadReviewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityReadReviewsBinding
    lateinit var reviewsAdapter: ReviewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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