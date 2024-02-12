package com.example.wizshop.api.jwtauth

import com.example.wizshop.common.helper.JwtHelper
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class RequestMemberResolver(
    private val jwtHelper: JwtHelper
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == RequestMember::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): RequestMember {
        val req = webRequest.nativeRequest as HttpServletRequest
        return req.getHeader("Authorization")
            .substring("Bearer ".length)
            .let {
                jwtHelper.getClaim(it) { claims ->
                    claims.subject.toLong()
                }
            }.let { RequestMember(it) }
    }
}