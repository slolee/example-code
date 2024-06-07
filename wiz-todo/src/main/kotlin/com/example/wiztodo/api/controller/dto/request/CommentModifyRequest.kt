package com.example.wiztodo.api.controller.dto.request

data class CommentModifyRequest(
    val contents: String,
    val writer: String,
    val password: String
)