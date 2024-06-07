package com.example.wiztodo.api.controller.dto.request

import com.example.wiztodo.domain.todo.entity.Todo

data class TodoCreateRequest(
    val title: String,
    val contents: String,
    val writer: String
) {

    fun toEntity() = Todo(
        title = title,
        contents = contents,
        writer = writer
    )
}