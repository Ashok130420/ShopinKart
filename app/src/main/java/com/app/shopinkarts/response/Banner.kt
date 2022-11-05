package com.app.shopinkarts.response

data class Banner(
    val _id: String,
    val bannerImage: String,
    val bannerName: String,
    val bannerType: Int,
    var bannerURL: String,
    val creationTimeStamp: String,
    val isActive: Int
)