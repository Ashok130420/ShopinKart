package com.example.shopinkarts.response

data class Variants(
    var color: String,
    var size: String,
    var quantity: Int,
    var price: Int,
    var id: String,
    var stock: Int,
)