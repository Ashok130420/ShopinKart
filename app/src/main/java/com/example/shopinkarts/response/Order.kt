package com.example.shopinkarts.response

data class Order(
    val _id: String,
    val creationTimeStamp: String,
    val discount: Int,
    val finalAmount: Int,
    val gstAmount: Int,
    val orderStatus: Int,
    val paymentType: Int,
    val products: List<ProductOrder>,
    val shippingDetails: ShippingDetails,
    val totalAmount: Int,
    val userId: String
)