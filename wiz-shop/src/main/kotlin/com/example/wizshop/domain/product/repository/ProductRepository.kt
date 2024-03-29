package com.example.wizshop.domain.product.repository

import com.example.wizshop.domain.product.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    // TODO : QueryDSL 로 수정
    @Query(
        value = "SELECT p FROM Product p JOIN FETCH p.seller WHERE p.name LIKE concat('%', :keyword, '%') OR p.description LIKE concat('%', :keyword, '%')",
        countQuery = "SELECT count(p) FROM Product p WHERE p.name LIKE concat('%', :keyword, '%') OR p.description LIKE concat('%', :keyword, '%')"
    )
    fun findAllByKeyword(keyword: String, pageable: PageRequest): Page<Product>
}