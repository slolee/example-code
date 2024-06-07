package com.example.wiztodo.api.controller.dto.response

import com.example.wiztodo.domain.todo.entity.Todo
import java.time.LocalDateTime

data class TodoSimpleResponse(
    val todoId: Long,
    val title: String,
    val writer: String,
    val isDone: Boolean,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(todo: Todo) = TodoSimpleResponse(
            todoId = todo.id!!,
            title = todo.title,
            writer = todo.writer,
            isDone = todo.isDone,
            createdAt = todo.createdAt
        )
    }
}