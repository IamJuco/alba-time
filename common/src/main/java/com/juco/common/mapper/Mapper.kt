package com.juco.common.mapper

import com.juco.common.model.UiPayDayType
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun UiPayDayType.displayName(): String {
    return when (this) {
        UiPayDayType.MONTHLY -> "월급"
        UiPayDayType.WEEKLY -> "주급"
//        UiPayDayType.CUSTOM -> "직접설정"
    }
}

fun LocalTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.toLocalTime(): LocalTime {
    return runCatching {
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    }.getOrElse { LocalTime.of(0, 0) }
}