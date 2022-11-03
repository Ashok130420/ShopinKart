package com.example.shopinkarts.response

data class VerifyOtpResponse(
    val message: String,
    val otp: String,
    val status: Boolean
)