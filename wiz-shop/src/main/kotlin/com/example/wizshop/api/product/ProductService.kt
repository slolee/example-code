package com.example.wizshop.api.product

import com.example.wizshop.api.product.dto.ProductDetailResponse
import com.example.wizshop.api.product.dto.ProductRegisterRequest
import com.example.wizshop.api.product.dto.ProductRegisterResponse
import com.example.wizshop.api.product.dto.ProductTitleResponse
import com.example.wizshop.domain.member.MemberRepository
import com.example.wizshop.domain.product.PopularSearchKeywordRedisRepository
import com.example.wizshop.domain.product.ProductRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
    private val popularSearchKeywordRedisRepository: PopularSearchKeywordRedisRepository
) {

    fun register(memberId: Long, req: ProductRegisterRequest): ProductRegisterResponse {
        val member = memberRepository.findByIdOrNull(memberId)!!
        return req.toEntity(seller = member)
            .let { productRepository.save(it) }
            .let { ProductRegisterResponse(it.id!!) }
    }

    @Transactional
    fun retrieve(productId: Long): ProductDetailResponse {
        return productRepository.findByIdOrNull(productId)
            ?.also { productRepository.save(it.addHit()) }
            ?.let { ProductDetailResponse.from(it) }
            ?: throw RuntimeException("상품 정보를 찾을 수 없습니다.")
    }

    @Transactional(readOnly = true)
    fun search(keyword: String, pageable: PageRequest): Page<ProductTitleResponse> {
        if (pageable.pageNumber == 0) popularSearchKeywordRedisRepository.increment(keyword)
        return productRepository.findAllByKeyword(keyword, pageable)
            .map { ProductTitleResponse.from(it) }
    }

    fun retrievePopularTop5(): List<String> {
        return popularSearchKeywordRedisRepository.findTopN(n = 5)
    }
}