package com.example.shopinkarts.model


data class CartModel(
    var pId: String,
    var vId: String,
    var itemName: String,
    var discountedPrice: String,
    var actualPrice: String,
    var color: String,
    var size: String,
    var quantity: String,
    var totalAmount: String,
    var imageUrl: String
)