package com.example.wizshop.common.helper

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

fun LocalDateTime.toDate() = this.toInstant(ZoneOffset.UTC).let { Date.from(it) }