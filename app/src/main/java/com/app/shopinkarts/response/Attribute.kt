package com.app.shopinkarts.response

data class Attribute(
    val id: String,
    val name: String,
    val types: List<String>,
    var isChecked: Boolean=false
)