package com.example.shopinkarts.response

data class MyOrdersResponse(
    val message: String,
    val orders: List<Order>,
    val status: Boolean
)