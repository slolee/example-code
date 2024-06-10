package com.example.wiztodo.api.controller

import com.example.wiztodo.api.controller.dto.request.MemberRegisterRequest
import com.example.wiztodo.api.service.MemberService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    fun registerMember(@RequestBody req: MemberRegisterRequest): ResponseEntity<Unit> {
        memberService.register(req)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @PostMapping("/signin")
    fun login(@RequestBody req: MemberRegisterRequest): ResponseEntity<String> {
        return memberService.login(req)
            .let { ResponseEntity.ok(it) }
    }
}