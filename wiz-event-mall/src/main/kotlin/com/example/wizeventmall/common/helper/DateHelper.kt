package com.example.wizeventmall.common.helper

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

fun LocalDateTime.toDate(): Date = this.toInstant(ZoneOffset.UTC).let { Date.from(it) }