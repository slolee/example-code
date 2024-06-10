package com.example.wiztodo.domain.todo.repository

import com.example.wiztodo.domain.todo.entity.QComment
import com.example.wiztodo.domain.todo.entity.QTodo
import com.example.wiztodo.domain.todo.entity.Todo
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository : JpaRepository<Todo, Long>, TodoQueryDslRepository {

}

interface TodoQueryDslRepository {
    fun findAllWithComment(): List<Todo>
}

class TodoQueryDslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : TodoQueryDslRepository {

    private val todo = QTodo.todo
    private val comment = QComment.comment

    override fun findAllWithComment(): List<Todo> {
        // SELECT * FROM todo t OUTER LEFT JOIN comment c ON t.id = c.todo_id;
        // 1. Batch Size
        // 2. Fetch Join
        // 3. EntityGraph
        return jpaQueryFactory.selectFrom(todo)
            .leftJoin(todo.comments, comment)
            .fetchJoin()
            .fetch()
    }
}