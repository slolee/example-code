package com.example.wiztodo.api.controller

import com.example.wiztodo.api.controller.dto.request.CommentCreateRequest
import com.example.wiztodo.api.controller.dto.request.CommentDeleteRequest
import com.example.wiztodo.api.controller.dto.request.CommentModifyRequest
import com.example.wiztodo.api.controller.dto.response.CommentResponse
import com.example.wiztodo.api.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos/{todoId}/comments")
class CommentController(
    private val commentService: CommentService
) {

    @PostMapping
    fun createComment(
        @PathVariable todoId: Long,
        @RequestBody req: CommentCreateRequest
    ): ResponseEntity<CommentResponse> {
        return commentService.create(todoId, req)
            .let { ResponseEntity.status(HttpStatus.CREATED).build() }
    }

    @PutMapping("/{commentId}")
    fun modifyComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody req: CommentModifyRequest
    ): ResponseEntity<CommentResponse> {
        return commentService.modify(todoId, commentId, req)
            .let { ResponseEntity.ok(it) }
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(
        @PathVariable todoId: Long,
        @PathVariable commentId: Long,
        @RequestBody req: CommentDeleteRequest
    ): ResponseEntity<Unit> {
        commentService.delete(todoId, commentId, req)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}