package com.example.shopinkarts.response

data class CategoryBannerResponse(
    val banners: List<Banner>,
    val message: String,
    val status: Boolean
)