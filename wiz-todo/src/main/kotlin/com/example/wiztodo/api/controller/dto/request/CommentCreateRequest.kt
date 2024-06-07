package com.example.wiztodo.api.controller.dto.request

import com.example.wiztodo.domain.todo.entity.Comment
import com.example.wiztodo.domain.todo.entity.Todo

data class CommentCreateRequest(
    val contents: String,
    val writer: String,
    val password: String
) {
    fun toEntity(todo: Todo) = Comment(
        contents = contents,
        writer = writer,
        password = password,
        todo = todo
    )
}