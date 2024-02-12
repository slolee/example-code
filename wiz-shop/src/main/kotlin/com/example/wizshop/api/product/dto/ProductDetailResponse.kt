package com.example.wizshop.api.product.dto

import com.example.wizshop.domain.product.entity.Product
import com.example.wizshop.domain.product.entity.ProductStatus
import java.time.LocalDateTime

data class ProductDetailResponse(
    val productName: String,
    val price: Long,
    val description: String,
    val hit: Long,
    val status: ProductStatus,
    val sellerNickname: String,
    val createdAt: LocalDateTime
) {

    companion object {
        fun from(product: Product) = ProductDetailResponse(
            productName = product.name,
            price = product.price,
            description = product.description,
            hit = product.hit,
            status = product.status,
            sellerNickname = product.seller.nickname,
            createdAt = product.createdAt
        )
    }
}