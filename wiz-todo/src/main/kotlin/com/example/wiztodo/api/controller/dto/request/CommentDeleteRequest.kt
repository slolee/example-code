package com.example.wiztodo.api.controller.dto.request

data class CommentDeleteRequest(
    val writer: String,
    val password: String
)