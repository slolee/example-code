package com.example.wiztodo.common

import org.springframework.stereotype.Component

@Component
class JwtHelper {

    fun generateAccessToken(id: Long): String {
        /**
         *  1. 얘는 그냥 주어진 바를 다하면된다. 자유롭게.
         *  2. 생각의 범위가 굉장히 좁아져요. -> 관심사가 분리됐다.
         *  3. 이 안에 코드(How)가 바뀌어도 외부에 영향(변경의 전파)을 주지 않는다.
         */
        return "fake_access_token"
    }

    fun validate(accessToken: String): Boolean {
        return true
    }
}