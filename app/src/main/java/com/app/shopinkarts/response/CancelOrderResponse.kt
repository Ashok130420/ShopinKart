package com.app.shopinkarts.response


data class CancelOrderResponse(
    val message: String,
    val order: Order,
    val status: Boolean
)