package com.app.shopinkarts.response


data class FlashSale(
    val _id: String,
    val attributes: List<Attribute>,
    val categoryId: String,
    val creationTimeStamp: String,
    val description: String,
    val discount: Int,
    val discountType: Int,
    val price: Int,
    val productImages: List<String>,
    val productName: String,
    val stock: Int,
    val subCategoryId: String,
    val tax: Int,
    val taxType: Int,
    val variantsArr: List<VariantsArr>,
    val avgRating: Double,
    val deliveryInstructions: List<String>,
    val dispatchDetails: List<String>,
    val isFreeDelivery: Int,
    val productCheckList: ProductCheckList,
    val reviews: List<Any>,

)