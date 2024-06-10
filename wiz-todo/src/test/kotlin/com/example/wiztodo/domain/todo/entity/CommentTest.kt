package com.example.wiztodo.domain.todo.entity

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CommentTest {

    @Test
    fun `validIsMine - 올바른 writer, password 를 입력시 TRUE 를 반환하는지 확인`() {
        // given
        val comment = Comment(
            id = 1L,
            contents = "TEST",
            writer = "박찬준",
            password = "1234",
            todo = Todo(id = 1L, title = "TEST", contents = "TEST", writer = "ch4njun")
        )

        // when
        val result = comment.validIsMine("박찬준", "1234")

        // then
        result shouldBe true
    }

    @Test
    fun `validIsMine - 올바르지 않은 wirter 혹은 password 를 입력시 FALSE 를 반환하는지 확인`() {
        // given
        val comment = Comment(
            id = 1L,
            contents = "TEST",
            writer = "박찬준",
            password = "1234",
            todo = Todo(id = 1L, title = "TEST", contents = "TEST", writer = "ch4njun")
        )

        // when
        val result1 = comment.validIsMine("박찬준1", "1234")
        val result2 = comment.validIsMine("박찬준", "1233")

        // then
        result1 shouldBe false
        result2 shouldBe false
    }
}