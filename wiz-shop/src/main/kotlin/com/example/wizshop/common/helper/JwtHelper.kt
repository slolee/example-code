package com.example.wizshop.common.helper

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Component
class JwtHelper(
    @Value("\${jwt.secret_key}") private val secretKey: String
) {

    private val key: Key by lazy {
        val encodedKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
        Keys.hmacShaKeyFor(encodedKey.toByteArray())
    }

    fun generateAccessToken(id: Long): String {
        val now = LocalDateTime.now()
        return Jwts.builder()
            .setSubject(id.toString())
            .setIssuedAt(now.toDate())
            .setExpiration(now.plusSeconds(60 * 60).toDate())
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
    }

    fun verify(accessToken: String): Boolean {
        try {
            val expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
                .expiration
            if (expiration.before(LocalDateTime.now().toDate())) {
                throw RuntimeException("토큰 만료")
            }
        } catch (ex: RuntimeException) {
            // TODO : 로그가 필요한 에러라면 catch 계층 분리해서 로그 남길것.
            return false
        }
        return true
    }

    fun <T> getClaim(accessToken: String, func: (Claims) -> T): T {
        return func(
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .body
        )
    }
}