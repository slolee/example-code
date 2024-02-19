package com.example.wizeventmall.api.jwtauth

import com.example.wizeventmall.common.helper.JwtHelper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtHelper: JwtHelper
) : OncePerRequestFilter() {

    override fun doFilterInternal(req: HttpServletRequest, resp: HttpServletResponse, filterChain: FilterChain) {
        if (this.matchExcludePath(req)) {
            filterChain.doFilter(req, resp)
        } else {
            val accessToken = req.getHeader("Authorization")?.substring("Bearer ".length)
            if (accessToken == null) {
                this.handleUnSuccessAuthentication(resp, "AccessToken 을 찾을 수 없습니다.")
            } else if (!jwtHelper.verify(accessToken)) {
                this.handleUnSuccessAuthentication(resp, "잘못된 AccessToken 입니다.")
            } else {
                filterChain.doFilter(req, resp)
            }
        }
    }

    private fun handleUnSuccessAuthentication(resp: HttpServletResponse, message: String) {
        resp.status = HttpStatus.UNAUTHORIZED.value()
        resp.contentType = "application/json; charset=UTF-8"
        resp.writer.print(message)
    }

    private fun matchExcludePath(req: HttpServletRequest): Boolean {
        return JWT_AUTH_EXCLUDE_PATH_LIST.map {
            AntPathRequestMatcher(it)
        }.let {
            OrRequestMatcher(it)
        }.matches(req)
    }

    companion object {
        private val JWT_AUTH_EXCLUDE_PATH_LIST = listOf(
            "/ping",
            "/api/v1/auth/**"
        )
    }
}