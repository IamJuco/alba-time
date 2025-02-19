package com.juco.workplacesetting.mapper

import com.juco.domain.model.PayDay
import com.juco.domain.model.PayDayType
import com.juco.domain.model.WorkTime
import com.juco.workplacesetting.model.UiPayDay
import com.juco.workplacesetting.model.UiPayDayType
import com.juco.workplacesetting.model.UiWorkTime
import com.juco.workplacesetting.util.convertToPayDays
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun UiPayDay.toDomain(): PayDay {
    return PayDay(
        type = when (this.type) {
            UiPayDayType.MONTHLY -> PayDayType.MONTHLY
            UiPayDayType.WEEKLY -> PayDayType.WEEKLY
            UiPayDayType.CUSTOM -> PayDayType.CUSTOM
        },
        dates = convertToPayDays(this.value)
    )
}

fun UiPayDayType.displayName(): String {
    return when (this) {
        UiPayDayType.MONTHLY -> "월급"
        UiPayDayType.WEEKLY -> "주급"
        UiPayDayType.CUSTOM -> "직접설정"
    }
}

fun UiWorkTime.toDomain(): WorkTime {
    return WorkTime(
        workStartTime = this.startTime,
        workEndTime = this.endTime
    )
}

fun LocalTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun String.toLocalTime(): LocalTime {
    return runCatching {
        LocalTime.parse(this, DateTimeFormatter.ofPattern("HH:mm"))
    }.getOrElse { LocalTime.of(0, 0) }
}