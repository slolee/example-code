package com.example.wiztodo.domain.todo.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
class Todo(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    var id: Long? = null,

    var title: String,
    var contents: String,
    val writer: String,
    var isDone: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "todo")
    val comments: List<Comment> = arrayListOf()
) {
    fun update(title: String, contents: String): Todo {
        this.title = title
        this.contents = contents
        return this
    }

    fun toggleIsDone(): Todo {
        this.isDone = !this.isDone
        return this
    }
}