package com.example.wiztodo.api.controller

import com.example.wiztodo.api.controller.dto.request.TodoCreateRequest
import com.example.wiztodo.api.controller.dto.request.TodoModifyRequest
import com.example.wiztodo.api.controller.dto.response.TodoResponse
import com.example.wiztodo.api.controller.dto.response.TodoSimpleResponse
import com.example.wiztodo.api.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/todos")
class TodoController(
    private val todoService: TodoService
) {

    @PostMapping
    fun createTodo(@RequestBody req: TodoCreateRequest): ResponseEntity<TodoResponse> {
        return todoService.create(req)
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }
    }

    @GetMapping("/{todoId}")
    fun retrieveTodo(@PathVariable todoId: Long): ResponseEntity<TodoResponse> {
        return todoService.retrieve(todoId)
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/simple")
    fun retrieveAllTodos(): ResponseEntity<List<TodoSimpleResponse>> {
        return todoService.retrieveAll()
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping
    fun retrieveAllTodoWithComments(): ResponseEntity<List<TodoResponse>> {
        return todoService.retrieveAllWithComment()
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("/{todoId}")
    fun modifyTodo(@PathVariable todoId: Long, @RequestBody req: TodoModifyRequest): ResponseEntity<TodoResponse> {
        return todoService.modify(todoId, req)
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("/{todoId}/done")
    fun toggleIsDone(@PathVariable todoId: Long) {
        todoService.toggleIsDone(todoId)
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(@PathVariable todoId: Long): ResponseEntity<Unit> {
        todoService.delete(todoId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}