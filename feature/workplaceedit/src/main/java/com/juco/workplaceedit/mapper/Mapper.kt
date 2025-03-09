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
    if (dates.isEmpty()) return ""

    val firstDate = dates.first()

    return when {
        // 모든 날짜가 같은 '일'인 경우 (1일, 5일, 말일 등)
        dates.all { it.dayOfMonth == firstDate.dayOfMonth } -> {
            if (YearMonth.from(firstDate).lengthOfMonth() == firstDate.dayOfMonth) "말일"
            else "${firstDate.dayOfMonth}일"
        }

        // 모든 날짜가 같은 '요일'인 경우 (월요일, 금요일 등)
        dates.all { it.dayOfWeek == firstDate.dayOfWeek } -> {
            when (firstDate.dayOfWeek) {
                DayOfWeek.MONDAY -> "월요일"
                DayOfWeek.TUESDAY -> "화요일"
                DayOfWeek.WEDNESDAY -> "수요일"
                DayOfWeek.THURSDAY -> "목요일"
                DayOfWeek.FRIDAY -> "금요일"
                DayOfWeek.SATURDAY -> "토요일"
                DayOfWeek.SUNDAY -> "일요일"
            }
        }

        // 커스텀 설정 (여러 날짜가 포함된 경우)
        else -> "직접설정"
    }
}