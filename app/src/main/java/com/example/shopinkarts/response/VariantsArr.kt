package com.example.shopinkarts.response

data class VariantsArr(
    val price: Int,
    val stock: String,
    val variant: String,

    var pId: String,
    var id: String,
    var discountedPrice: String,
    var actualPrice: String,
    var variantValue: String,

)