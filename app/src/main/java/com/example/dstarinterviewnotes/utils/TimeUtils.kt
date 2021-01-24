package com.example.dstarinterviewnotes.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun toDateTime(timestamp : Long) : String
    = Instant.ofEpochSecond(timestamp / 1000)
            .atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", ))