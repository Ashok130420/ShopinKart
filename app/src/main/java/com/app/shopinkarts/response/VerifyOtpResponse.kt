package com.app.shopinkarts.response

data class VerifyOtpResponse(
    val message: String,
    val otp: String,
    val status: Boolean
)