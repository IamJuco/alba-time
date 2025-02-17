package com.juco.feature.calendar.util

import java.time.LocalDate

// 대체공휴일 계산
fun substituteHolidaysCalculator(holidays: List<LocalDate>): List<LocalDate> {
    val substituteHolidays = mutableListOf<LocalDate>()
    val existingHolidays = holidays.toMutableSet()

    holidays.forEach { holiday ->
        if (holiday.dayOfWeek.value == 6) { // 토요일
            var substitute = holiday.plusDays(2) // 다음 월요일
            while (existingHolidays.contains(substitute)) {
                substitute = substitute.plusDays(1) // 공휴일과 겹치지 않는 월요일
            }
            substituteHolidays.add(substitute)
            existingHolidays.add(substitute) // 추가된 대체공휴일도 체크
        } else if (holiday.dayOfWeek.value == 7) { // 일요일
            var substitute = holiday.plusDays(1) // 다음 월요일
            while (existingHolidays.contains(substitute)) {
                substitute = substitute.plusDays(1) // 공휴일과 겹치지 않는 월요일
            }
            substituteHolidays.add(substitute)
            existingHolidays.add(substitute) // 추가된 대체공휴일도 체크
        }
    }
    return substituteHolidays
}