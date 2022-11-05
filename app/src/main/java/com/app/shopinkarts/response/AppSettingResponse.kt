package com.app.shopinkarts.response

data class AppSettingResponse(
    val appSetting: AppSetting,
    val message: String,
    val status: Boolean
)