package com.juco.workplaceedit.mapper

import com.juco.common.model.UiPayDay
import com.juco.common.model.UiPayDayType
import com.juco.common.model.UiTaxType
import com.juco.common.model.UiWorkTime
import com.juco.common.util.convertToPayDays
import com.juco.domain.model.PayDay
import com.juco.domain.model.PayDayType
import com.juco.domain.model.WorkTime
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun UiPayDay.toDomain(): PayDay {
    return PayDay(
        type = when (this.type) {
            UiPayDayType.MONTHLY -> PayDayType.MONTHLY
            UiPayDayType.WEEKLY -> PayDayType.WEEKLY
//            UiPayDayType.CUSTOM -> PayDayType.CUSTOM
        },
        dates = convertToPayDays(this.value)
    )
}

fun UiWorkTime.toDomain(): WorkTime {
    return WorkTime(
        workStartTime = this.startTime,
        workEndTime = this.endTime
    )
}

fun PayDay.toUiModel(): UiPayDay {
    return UiPayDay(
        type = when (this.type) {
            PayDayType.MONTHLY -> UiPayDayType.MONTHLY
            PayDayType.WEEKLY -> UiPayDayType.WEEKLY
//            PayDayType.CUSTOM -> UiPayDayType.CUSTOM
        },
        value = convertPayDayToString(this.dates)
    )
}

fun WorkTime.toUiModel(): UiWorkTime {
    return UiWorkTime(
        startTime = this.workStartTime,
        endTime = this.workEndTime
    )
}

fun Float.toUiModel(): UiTaxType {
    return UiTaxType.entries.find { it.rate == this } ?: UiTaxType.NONE
}

fun convertPayDayToString(dates: List<LocalDate>): String {
    if (dates.isEmpty()) return "설정되지 않음"

    val firstDate = dates.first()

    return when {
        dates.all { it.dayOfMonth == firstDate.dayOfMonth } -> {
            "${firstDate.dayOfMonth}일"
        }

        dates.all { it.dayOfWeek == firstDate.dayOfWeek } -> {
            firstDate.dayOfWeek.toKorean()
        }

        else -> "직접설정"
    }
}

// 요일을 한국어로 변환하는 확장 함수
fun DayOfWeek.toKorean(): String {
    return when (this) {
        DayOfWeek.MONDAY -> "월요일"
        DayOfWeek.TUESDAY -> "화요일"
        DayOfWeek.WEDNESDAY -> "수요일"
        DayOfWeek.THURSDAY -> "목요일"
        DayOfWeek.FRIDAY -> "금요일"
        DayOfWeek.SATURDAY -> "토요일"
        DayOfWeek.SUNDAY -> "일요일"
    }
}