package com.example.shopinkarts.response

data class CustomerSupportResponse(
    val fullName: String,
    val message: String,
    val phone: String,
    val productId: String
)