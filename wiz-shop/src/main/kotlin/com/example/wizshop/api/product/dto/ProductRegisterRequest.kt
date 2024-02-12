package com.example.wizshop.api.product.dto

data class ProductRegisterRequest(
    val productName: String,
    val amount: Long,
    val description: String
) {
}