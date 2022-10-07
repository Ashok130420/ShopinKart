package com.example.shopinkarts.response

import com.example.shopinkarts.model.CreateProduct
import com.example.shopinkarts.model.ShippingDetails

data class Order(
    val _id: String,
    val creationTimeStamp: String,
    val discount: Float,
    val finalAmount: Float,
    val gstAmount: Float,
    val orderStatus: Int,
    val paymentType: Int,
    val products: List<CreateProduct>,
    val shippingDetails: ShippingDetails,
    val totalAmount: Double,
    val userId: String,
    val orderId: String,
    val trackingDetails: List<TrackOrder>,



)