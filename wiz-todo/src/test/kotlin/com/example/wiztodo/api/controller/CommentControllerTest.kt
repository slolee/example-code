package com.example.wiztodo.api.controller

import com.example.wiztodo.api.controller.dto.response.CommentResponse
import com.example.wiztodo.api.service.CommentService
import com.example.wiztodo.common.JwtHelper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.LocalDateTime

@WebMvcTest(CommentController::class)
@Import(value = [JwtHelper::class])
@AutoConfigureMockMvc
class CommentControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,

    @MockkBean
    private val commentService: CommentService
) {

    @Test
    fun `createComment - 성공 케이스`() {
        // given
        val todoId = 1L
        val requestBody = "{\"contents\":\"TEST\",\"writer\":\"ch4njun\",\"password\":\"1234\"}"
        every { commentService.create(any(), any()) } returns CommentResponse(
            contents = "TEST",
            writer = "ch4njun",
            createdAt = LocalDateTime.of(2024, 6, 10, 16, 0)
        )

        // when
        val resp = mockMvc.perform(
            post("/api/todos/$todoId/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(requestBody)
        ).andReturn()

        // then
        resp.response.status shouldBe HttpStatus.CREATED.value()
        resp.response.contentAsString shouldBe "{\"contents\":\"TEST\",\"writer\":\"ch4njun\",\"createdAt\":\"2024-06-10T14:00:00\"}"
    }

}