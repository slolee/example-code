package com.example.wizshop.api.product

import com.example.wizshop.api.jwtauth.RequestMember
import com.example.wizshop.api.product.dto.ProductRegisterRequest
import com.example.wizshop.api.product.dto.ProductRegisterResponse
import com.example.wizshop.api.product.dto.ProductTitleResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v2/products")
class ProductControllerV2(
    private val productService: ProductServiceV2
) {

    @PostMapping
    fun registerProduct(
        member: RequestMember,
        @RequestBody req: ProductRegisterRequest
    ): ResponseEntity<ProductRegisterResponse> {
        return productService.register(member.id, req)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Unit> {
        productService.delete(productId)
        return ResponseEntity.noContent().build()
    }

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