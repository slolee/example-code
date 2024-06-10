package com.example.wiztodo.domain.member.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Member(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    val email: String,
    val password: String
) {

    fun validPassword(password: String): Boolean {
        return this.password == password
    }
}