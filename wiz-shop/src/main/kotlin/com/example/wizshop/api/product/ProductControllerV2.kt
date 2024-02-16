package com.example.wizshop.api.product

import com.example.wizshop.api.product.dto.ProductTitleResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v2/products")
class ProductControllerV2(
    private val productService: ProductService
) {

    @GetMapping("/search")
    fun search(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<ProductTitleResponse>> {
        val pageable = PageRequest.of(page - 1, size)
        return productService.searchWithCache(keyword, pageable)
            .let { ResponseEntity.ok(it) }
    }
}