package com.example.wizshop.api.product.dto

data class ProductRegisterRequest(
    val productName: String,
    val price: Long,
    val description: String
) {
}