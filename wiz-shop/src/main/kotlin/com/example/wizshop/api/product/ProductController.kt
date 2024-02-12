package com.example.wizshop.api.product

import com.example.wizshop.api.jwtauth.RequestMember
import com.example.wizshop.api.product.dto.ProductDetailResponse
import com.example.wizshop.api.product.dto.ProductRegisterRequest
import com.example.wizshop.api.product.dto.ProductRegisterResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @PostMapping
    fun registerProduct(
        member: RequestMember,
        @RequestBody req: ProductRegisterRequest
    ): ResponseEntity<ProductRegisterResponse> {
        return productService.register(member.id, req)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @GetMapping("/{productId}")
    fun retrieve(@PathVariable productId: Long): ResponseEntity<ProductDetailResponse> {
        return productService.retrieveBy(productId)
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/search")
    fun retrieveByKeyword() {
        // TODO
    }
}