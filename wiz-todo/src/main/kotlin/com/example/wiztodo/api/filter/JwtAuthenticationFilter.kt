package com.example.wiztodo.api.filter

import com.example.wiztodo.common.JwtHelper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtHelper: JwtHelper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        req: HttpServletRequest,
        resp: HttpServletResponse,
        chain: FilterChain
    ) {
        /**
         * 1. 사용자로부터 인증에 필요한 AccessToken 을 받는다. 만약 없으면 인증 실패로 처리한다.
         * 2. AccessToken 이 정상적인지 검증한다.
         * (그다지 관심은 없지만.. 이 검증에는 토큰의 만료기간이 유효한지, 내가 만든 것인지 등을 검증한다)
         */
        val accessToken = req.getAccessToken() ?: throw RuntimeException()
        if (!jwtHelper.validate(accessToken)) {
            throw RuntimeException()
        }
        // if validate success
        chain.doFilter(req, resp)
    }

    fun HttpServletRequest.getAccessToken(): String? {
        return this.getHeader("Authorization") // Bearer {access_token}
            ?.substring("Bearer ".length)
    }
}