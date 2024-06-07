package com.example.wiztodo.api.controller.dto.response

import com.example.wiztodo.domain.todo.entity.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val contents: String,
    val writer: String,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(comment: Comment) = CommentResponse(
            contents = comment.contents,
            writer = comment.writer,
            createdAt = comment.createdAt
        )
    }
}