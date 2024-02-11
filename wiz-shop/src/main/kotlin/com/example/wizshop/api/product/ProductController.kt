package com.example.wizshop.api.product

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController {

    @PostMapping
    fun registerProduct() {
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