package com.example.wizshop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class WizShopApplication

fun main(args: Array<String>) {
    runApplication<WizShopApplication>(*args)
}
