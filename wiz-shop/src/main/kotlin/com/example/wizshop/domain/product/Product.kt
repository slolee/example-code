package com.example.wizshop.domain.product

import com.example.wizshop.common.BaseEntity
import com.example.wizshop.domain.member.Member
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    var id: Long? = null,

    var name: String,
    var price: Long,
    var description: String,
    var hit: Long = 0,
    @Enumerated(value = EnumType.STRING)
    var status: ProductStatus,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    val seller: Member

): BaseEntity() {

    fun addHit(): Product {
        this.hit += 1
        return this
    }
}

enum class ProductStatus {
    ON_DISPLAY, SOLD_OUT, CLOSED
}