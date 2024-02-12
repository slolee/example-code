package com.example.wizshop.api.product

import com.example.wizshop.api.product.dto.ProductDetailResponse
import com.example.wizshop.api.product.dto.ProductRegisterRequest
import com.example.wizshop.api.product.dto.ProductRegisterResponse
import com.example.wizshop.domain.member.MemberRepository
import com.example.wizshop.domain.product.ProductRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository
) {

    fun register(memberId: Long, req: ProductRegisterRequest): ProductRegisterResponse {
        val member = memberRepository.findByIdOrNull(memberId)!!
        return req.toEntity(seller = member)
            .let { productRepository.save(it) }
            .let { ProductRegisterResponse(it.id!!) }
    }

    @Transactional(readOnly = true)
    fun retrieveBy(productId: Long): ProductDetailResponse {
        return productRepository.findByIdOrNull(productId)
            ?.let { ProductDetailResponse.from(it) }
            ?: throw RuntimeException("상품 정보를 찾을 수 없습니다.")
    }

}