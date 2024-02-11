package com.example.wizshop.api.member

import com.example.wizshop.api.member.dto.MemberLoginRequest
import com.example.wizshop.api.member.dto.MemberRegisterRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val memberService: MemberService
) {

    @PostMapping("/register")
    fun register(@RequestBody req: MemberRegisterRequest): ResponseEntity<Unit> {
        return memberService.register(req)
            .let { ResponseEntity.ok(it) }
    }

    @PostMapping("/login")
    fun login(@RequestBody req: MemberLoginRequest): ResponseEntity<String> {
        return memberService.login(req)
            .let { ResponseEntity.ok(it) }
    }
}