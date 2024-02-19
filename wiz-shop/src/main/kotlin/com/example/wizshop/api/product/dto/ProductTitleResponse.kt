package com.example.wizshop.api.product.dto

import com.example.wizshop.domain.product.entity.Product
import com.example.wizshop.domain.product.entity.ProductStatus
import java.time.LocalDateTime

data class ProductTitleResponse(
    val productId: Long,
    val productName: String,
    val price: Long,
    val hit: Long,
    val status: ProductStatus,
    val sellerNickname: String,
    val createdAt: LocalDateTime
) {

    companion object {
        fun from(product: Product) = ProductTitleResponse(
            productId = product.id!!,
            productName = product.name,
            price = product.price,
            hit = product.hit,
            status = product.status,
            sellerNickname = product.seller.nickname,
            createdAt = product.createdAt
        )
    }
}