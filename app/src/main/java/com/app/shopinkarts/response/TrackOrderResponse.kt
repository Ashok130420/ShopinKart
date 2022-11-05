package com.app.shopinkarts.response

data class TrackOrderResponse(
    val message: String,
    val order: List<TrackOrder>,
    val status: Boolean
)


