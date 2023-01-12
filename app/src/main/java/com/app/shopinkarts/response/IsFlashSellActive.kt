package com.app.shopinkarts.response

data class IsFlashSellActive(
    val _id: String,
    val creationTimeStamp: String,
    val endTime: String,
    val isActive: Int,
    val startDate: String,
    val startTime: String
)