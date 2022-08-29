package com.example.shopinkarts.response

data class DashBoardResponse(
    var banners: List<Any>,
    var message: String,
    var shopFor: List<ShopFor>,
    var status: Boolean
)