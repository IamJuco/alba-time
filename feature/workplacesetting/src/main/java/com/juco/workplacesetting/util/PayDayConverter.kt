package com.juco.workplacesetting.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

fun convertToPayDays(value: String): List<LocalDate> {
    val today = LocalDate.now()
    // 1년 데이터
    val payDaysForOneYear = today.plusYears(1)

    return when {
        // 1일, 5일, 말일 등
        value.matches(Regex("""\d+일|말일""")) -> {
            convertToMonthlyPayDays(today, payDaysForOneYear, value)
        }

        // 월요일 ~ 일요일
        value.matches(Regex("""[월화수목금토일]요일""")) -> {
            convertToWeeklyPayDays(today, payDaysForOneYear, value)
        }

        else -> emptyList()
    }
}

fun convertToMonthlyPayDays(startDate: LocalDate, endDate: LocalDate, value: String): List<LocalDate> {
    return generateSequence(startDate) { it.plusMonths(1) }
        .takeWhile { !it.isAfter(endDate) }
        .map { date ->
            when (value) {
                "말일" -> YearMonth.from(date).atEndOfMonth()
                else -> {
                    val day = value.replace("일", "").toIntOrNull() ?: 1
                    val yearMonth = YearMonth.from(date)
                    if (day > yearMonth.lengthOfMonth()) {
                        yearMonth.atEndOfMonth()
                    } else {
                        yearMonth.atDay(day)
                    }
                }
            }
        }
        .toList()
}

fun convertToWeeklyPayDays(startDate: LocalDate, endDate: LocalDate, value: String): List<LocalDate> {
    val dayOfWeek = when (value) {
        "월요일" -> DayOfWeek.MONDAY
        "화요일" -> DayOfWeek.TUESDAY
        "수요일" -> DayOfWeek.WEDNESDAY
        "목요일" -> DayOfWeek.THURSDAY
        "금요일" -> DayOfWeek.FRIDAY
        "토요일" -> DayOfWeek.SATURDAY
        "일요일" -> DayOfWeek.SUNDAY
        else -> return emptyList()
    }

    // 현재 날짜부터 가장 가까운 해당 요일
    val firstDayOnToday = startDate.with(java.time.temporal.TemporalAdjusters.nextOrSame(dayOfWeek))

    return generateSequence(firstDayOnToday) { it.plusWeeks(1) }
        .takeWhile { !it.isAfter(endDate) }
        .toList()
}