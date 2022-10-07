package com.example.shopinkarts.response

data class AppSettingResponse(
    val appSetting: AppSetting,
    val message: String,
    val status: Boolean
)