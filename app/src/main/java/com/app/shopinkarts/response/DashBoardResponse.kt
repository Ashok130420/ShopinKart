package com.app.shopinkarts.response

data class DashBoardResponse(
    val banners: List<Banner>,
    val dealOfDay: List<DealOfDay>,
    val flashSale: List<FlashSale>,
    val message: String,
    val mostPopular: List<MostPopular>,
    val newlyAdded: List<NewlyAdded>,
    val preferredManufacturers: List<PreferredManufacturer>,
    val shopFor: List<ShopFor>,
    val status: Boolean,
    val topRated: List<TopRated>,
    val discountForYou: List<DiscountForYou>,
    val recommendedItems: List<RecommendedItem>,

)