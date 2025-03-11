package com.juco.workplaceedit.util

import com.juco.common.model.WorkDayType
import java.time.LocalDate

fun workDayTypeSetter(workDays: List<LocalDate>): WorkDayType? {
    if (workDays.isEmpty()) return null

    val workDaySet = workDays.map { it.dayOfWeek }.toSet()

    return when (workDaySet) {
        WorkDayType.WEEKDAYS.dayOfWeeks -> WorkDayType.WEEKDAYS
        WorkDayType.WEEKENDS.dayOfWeeks -> WorkDayType.WEEKENDS
        else -> WorkDayType.CUSTOM
    }
}

fun getWorkDaysSummary(workDays: List<LocalDate>, workDayType: WorkDayType?): String {
    if (workDays.isEmpty()) return "설정 안됨"

    return when (workDayType) {
        WorkDayType.WEEKDAYS -> WorkDayType.WEEKDAYS.displayName
        WorkDayType.WEEKENDS -> WorkDayType.WEEKENDS.displayName
        WorkDayType.CUSTOM -> {
            val firstDay = workDays.first()
            val count = workDays.size - 1
            val formattedFirstDay = "${firstDay.monthValue}월 ${firstDay.dayOfMonth}일"
            if (count > 0) "$formattedFirstDay 외 ${count}개" else formattedFirstDay
        }
        null -> "설정 안됨"
    }
}