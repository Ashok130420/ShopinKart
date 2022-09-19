package com.example.shopinkarts.response

data class OrdersResponse(
    val message: String,
    val order: Order,
    val status: Boolean
)