package com.example.wiztodo.api.service

import com.example.wiztodo.api.controller.dto.request.TodoCreateRequest
import com.example.wiztodo.api.controller.dto.request.TodoModifyRequest
import com.example.wiztodo.api.controller.dto.response.TodoResponse
import com.example.wiztodo.api.controller.dto.response.TodoSimpleResponse
import com.example.wiztodo.domain.todo.repository.CommentRepository
import com.example.wiztodo.domain.todo.repository.TodoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TodoService(
    private val todoRepository: TodoRepository,
    private val commentRepository: CommentRepository
) {

    fun create(req: TodoCreateRequest): TodoResponse {
        return req.toEntity()
            .let { todoRepository.save(it) }
            .let { TodoResponse.from(it) }
    }

    fun retrieve(todoId: Long): TodoResponse {
        return todoRepository.findByIdOrNull(todoId)
            ?.let { TodoResponse.from(it) }
            ?: throw RuntimeException()
    }

    fun retrieveAll(): List<TodoSimpleResponse> {
        return todoRepository.findAll()
            .map { TodoSimpleResponse.from(it) }
    }

    fun retrieveAllWithComment(): List<TodoResponse> {
        return todoRepository.findAll()  // FIXME : QueryDSL 예제 적용
            .map { TodoResponse.from(it) }
    }

    @Transactional
    fun modify(todoId: Long, req: TodoModifyRequest): TodoResponse {
        return todoRepository.findByIdOrNull(todoId)
            ?.let {
                val (title, contents) = req
                it.update(title, contents)  // Apply Dirty Checking
            }?.let {
                TodoResponse.from(it)
            } ?: throw RuntimeException()
    }

    @Transactional
    fun toggleIsDone(todoId: Long) {
        todoRepository.findByIdOrNull(todoId)
            ?.toggleIsDone()  // Apply Dirty Checking
            ?: throw RuntimeException()
    }

    @Transactional
    fun delete(todoId: Long) {
        commentRepository.deleteAllByTodoId(todoId)
        todoRepository.deleteById(todoId)
    }
}