package com.example.wizeventmall

import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [
	SecurityAutoConfiguration::class,
	ManagementWebSecurityAutoConfiguration::class
])
class WizEventMallApplication

fun main(args: Array<String>) {
	runApplication<WizEventMallApplication>(*args)
}
