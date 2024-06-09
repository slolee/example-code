package com.example.wiztodo.domain.todo.repository

import com.example.wiztodo.domain.todo.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {

    @Modifying
    fun deleteAllByTodoId(todoId: Long)
}