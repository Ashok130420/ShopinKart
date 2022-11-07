package com.app.shopinkarts.model

data class CreateOrderRequest(
    val discount: String,
    val finalAmount: String,
    val gstAmount: String,
    val paymentType: String,
    val products: List<CreateProduct>,
    val shippingDetails: ShippingDetails,
    val totalAmount: String,
    val userId: String,
    val businessOperatingState: String,
    val companyName: String,
    val deliveryInstruction: String,


)