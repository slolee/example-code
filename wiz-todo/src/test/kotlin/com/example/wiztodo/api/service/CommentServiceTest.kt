package com.example.wiztodo.api.service

import com.example.wiztodo.api.controller.dto.request.CommentCreateRequest
import com.example.wiztodo.domain.todo.entity.Comment
import com.example.wiztodo.domain.todo.entity.Todo
import com.example.wiztodo.domain.todo.repository.CommentRepository
import com.example.wiztodo.domain.todo.repository.TodoRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

class CommentServiceTest {

    private val todoRepository = mockk<TodoRepository>()
    private val commentRepository = mockk<CommentRepository>()
    private val commentService = CommentService(todoRepository, commentRepository)

    @Test
    fun `create - 존재하지 않는 todoId 라면 RuntimeException 이 발생하는지 확인`() {
        // given
        val todoId = 1L
        val req = CommentCreateRequest(
            contents = "TEST",
            writer = "박찬준",
            password = "1234"
        )
        every { todoRepository.findByIdOrNull(todoId) } returns null

        // when & then
        shouldThrow<RuntimeException> {
            commentService.create(todoId, req)
        }.let {
            // it.message shouldBe ""
            // TODO : 예외 객체에대한 구체적인 검증도 가능합니다.
        }
    }

    @Test
    fun `create - 성공 케이스`() {
        // given
        val todoId = 1L
        val req = CommentCreateRequest(
            contents = "TEST",
            writer = "박찬준",
            password = "1234"
        )
        val todo = Todo(
            id = 1L,
            title = "TEST TODO",
            contents = "TEST CONTENTS",
            writer = "ch4njun",
            isDone = false,
            createdAt = LocalDateTime.of(2024, 6, 10, 15, 54)
        )
        every { todoRepository.findByIdOrNull(todoId) } returns todo
        every { commentRepository.save(any()) } returns Comment(
            id = 1L,
            contents = "TEST",
            writer = "박찬준",
            password = "1234",
            todo = todo,
            createdAt = LocalDateTime.of(2024, 6, 10, 15, 55)
        )

        // when
        val resp = commentService.create(todoId, req)

        // then
        verify(exactly = 1) { commentRepository.save(any()) }
        resp.contents shouldBe "TEST"
        resp.writer shouldBe "박찬준"
        resp.createdAt shouldBe LocalDateTime.of(2024, 6, 10, 15, 55)
    }
}