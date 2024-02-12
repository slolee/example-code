package com.example.wizshop.api.product.dto

import com.example.wizshop.domain.product.Product
import com.example.wizshop.domain.product.ProductStatus

data class ProductDetailResponse(
    val productName: String,
    val price: Long,
    val description: String,
    val hit: Long,
    val status: ProductStatus,
    val sellerNickname: String
) {

    companion object {
        fun from(product: Product) = ProductDetailResponse(
            productName = product.name,
            price = product.price,
            description = product.description,
            hit = product.hit,
            status = product.status,
            sellerNickname = product.seller.nickname
        )
    }
}