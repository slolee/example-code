package com.example.wizshop.api.product

import com.example.wizshop.api.product.dto.ProductRegisterRequest
import com.example.wizshop.api.product.dto.ProductRegisterResponse
import com.example.wizshop.api.product.dto.ProductTitleResponse
import com.example.wizshop.domain.member.repository.MemberRepository
import com.example.wizshop.domain.product.repository.PopularSearchKeywordRedisRepository
import com.example.wizshop.domain.product.repository.ProductRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductServiceV2(
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val popularSearchKeywordRedisRepository: PopularSearchKeywordRedisRepository
) {

    @CacheEvict(cacheNames = ["PRODUCT_SEARCH"], allEntries = true)
    fun register(memberId: Long, req: ProductRegisterRequest): ProductRegisterResponse {
        val member = memberRepository.findByIdOrNull(memberId)!!
        return req.toEntity(seller = member)
            .let { productRepository.save(it) }
            .let { ProductRegisterResponse(it.id!!) }
    }

    @CacheEvict(cacheNames = ["PRODUCT_SEARCH"], allEntries = true)
    fun delete(productId: Long) {
        productRepository.deleteById(productId)
    }

    @Cacheable(cacheNames = ["PRODUCT_SEARCH"], key = "#keyword + #pageable.pageNumber")
    @Transactional(readOnly = true)
    fun searchWithCache(keyword: String, pageable: PageRequest): Page<ProductTitleResponse> {
        if (pageable.pageNumber == 0) popularSearchKeywordRedisRepository.increment(keyword)
        return productRepository.findAllByKeyword(keyword, pageable)
            .map { ProductTitleResponse.from(it) }
    }
}