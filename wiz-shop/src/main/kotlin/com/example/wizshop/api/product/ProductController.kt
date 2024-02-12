package com.example.wizshop.api.product

import com.example.wizshop.api.jwtauth.RequestMember
import com.example.wizshop.api.product.dto.ProductRegisterRequest
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
    ) {
        println(member)
        // TODO
    }

    @GetMapping("/{productId}")
    fun retrieve(@PathVariable productId: Long) {
        // TODO
    }

    @GetMapping("/search")
    fun retrieveByKeyword() {
        // TODO
    }
}