package com.example.wiztodo.api.service

import com.example.wiztodo.api.controller.dto.request.CommentCreateRequest
import com.example.wiztodo.api.controller.dto.request.CommentDeleteRequest
import com.example.wiztodo.api.controller.dto.request.CommentModifyRequest
import com.example.wiztodo.api.controller.dto.response.CommentResponse
import com.example.wiztodo.domain.todo.repository.CommentRepository
import com.example.wiztodo.domain.todo.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
) {

    fun create(todoId: Long, req: CommentCreateRequest): CommentResponse {
        return todoRepository.findByIdOrNull(todoId)
            ?.let { commentRepository.save(req.toEntity(it)) }
            ?.let { CommentResponse.from(it) }
            ?: throw RuntimeException()
    }

    @Transactional
    fun modify(todoId: Long, commentId: Long, req: CommentModifyRequest): CommentResponse {
        if (!todoRepository.existsById(todoId)) throw RuntimeException()
        return commentRepository.findByIdOrNull(commentId)
            ?.let {
                check(it.validIsMine(req.writer, req.password))
                it.update(req.contents)  // Apply Dirty Checking
            }?.let {
                CommentResponse.from(it)
            } ?: throw RuntimeException()
    }

    fun delete(todoId: Long, commentId: Long, req: CommentDeleteRequest) {
        if (!todoRepository.existsById(todoId)) throw RuntimeException()
        commentRepository.findByIdOrNull(commentId)
            ?.let {
                check(it.validIsMine(req.writer, req.password))
                commentRepository.delete(it)
            }
    }
}