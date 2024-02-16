package com.example.wizshop.domain.config.cache

enum class CacheType(
    val timeToLive: Long
) {

    PRODUCT_SEARCH(10 * 60)
}