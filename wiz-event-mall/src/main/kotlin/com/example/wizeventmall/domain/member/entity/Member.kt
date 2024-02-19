package com.example.wizeventmall.domain.member.entity

import com.example.wizeventmall.common.BaseEntity
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
@Table(name = "member")
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    var id: Long? = null,

    val email: String,
    var password: String,
    var nickname: String

): BaseEntity() {

    fun encodePassword(encoder: PasswordEncoder) {
        this.password = encoder.encode(password)
    }
}