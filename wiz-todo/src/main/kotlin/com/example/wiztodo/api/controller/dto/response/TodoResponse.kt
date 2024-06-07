package com.example.wiztodo.api.controller.dto.response

import com.example.wiztodo.domain.todo.entity.Comment
import com.example.wiztodo.domain.todo.entity.Todo
import java.time.LocalDateTime

data class TodoResponse(
    val todoId: Long,
    val title: String,
    val contents: String,
    val writer: String,
    val isDone: Boolean,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse>
) {

    companion object {
        fun from(todo: Todo) = TodoResponse(
            todoId = todo.id!!,
            title = todo.title,
            contents = todo.contents,
            writer = todo.writer,
            isDone = todo.isDone,
            createdAt = todo.createdAt,
            comments = todo.comments.map { CommentResponse.from(it) }
        )
    }
}