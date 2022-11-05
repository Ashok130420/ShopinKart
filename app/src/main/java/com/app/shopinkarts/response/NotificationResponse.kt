package com.app.shopinkarts.response

data class NotificationResponse(
    val message: String,
    val notifications: List<Notification>,
    val status: Boolean
)