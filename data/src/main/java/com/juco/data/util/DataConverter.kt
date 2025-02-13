package com.juco.data.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

// "yyyy-MM-dd" 포맷
private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

fun String.toLocalDateList(): List<LocalDate> {
    return if (this.isEmpty()) emptyList()
    else this.split(",").mapNotNull { dateString ->
        runCatching { LocalDate.parse(dateString, formatter) }.getOrNull()
    }
}

fun List<LocalDate>.toDateString(): String {
    return this.joinToString(",") { it.format(formatter) }
}