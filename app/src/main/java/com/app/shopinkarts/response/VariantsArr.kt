package com.app.shopinkarts.response

data class VariantsArr(
    val price: Int,
    val stock: Int,
    val variant: String,


    var pId: String,
    var id: String,
    var discountedPrice: String,
    var actualPrice: Int,
    var variantValue: String,
    val discountType: Int,
    val discount: Int,



)