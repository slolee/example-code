package com.example.wiztodo.domain.todo.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    var id: Long? = null,

    var contents: String,
    val writer: String,
    val password: String,

    @ManyToOne
    @JoinColumn(name = "todo_id", referencedColumnName = "todo_id")
    val todo: Todo,

    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun validIsMine(writer: String, password: String): Boolean {
        return this.writer == writer && this.password == password
    }

    fun update(contents: String): Comment {
        this.contents = contents
        return this
    }
}