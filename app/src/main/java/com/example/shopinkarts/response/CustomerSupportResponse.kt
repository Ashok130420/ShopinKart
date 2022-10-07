package com.example.shopinkarts.response

data class CustomerSupportResponse(
    val email: String,
    val fullName: String,
    val isReturnRequest: String,
    val message: String,
    val orderId: String,
    val phone: String,
    val productId: String
)