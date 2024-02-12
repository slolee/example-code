package com.example.wizshop.api.product.dto

import com.example.wizshop.domain.member.Member
import com.example.wizshop.domain.product.Product
import com.example.wizshop.domain.product.ProductStatus

data class ProductRegisterRequest(
    val productName: String,
    val price: Long,
    val description: String
) {

    fun toEntity(seller: Member) = Product(
        name = productName,
        price = price,
        description = description,
        status = ProductStatus.ON_DISPLAY,
        seller = seller
    )
}