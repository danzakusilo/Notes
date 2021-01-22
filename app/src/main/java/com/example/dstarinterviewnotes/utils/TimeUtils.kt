package com.example.dstarinterviewnotes.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


fun toDateTime(timestamp : Long) : String {
    val dt = Instant.ofEpochSecond(timestamp / 1000)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", ))
    return dt
}