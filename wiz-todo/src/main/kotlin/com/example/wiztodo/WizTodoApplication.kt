package com.example.wiztodo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WizTodoApplication

fun main(args: Array<String>) {
    runApplication<WizTodoApplication>(*args)
}
