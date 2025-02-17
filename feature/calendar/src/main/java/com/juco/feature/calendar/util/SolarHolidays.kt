package com.juco.feature.calendar.util

import java.time.LocalDate

// 양력 공휴일
fun solarHolidays(year: Int): List<LocalDate> {
    return listOf(
        LocalDate.of(year, 1, 1),  // 새해
        LocalDate.of(year, 3, 1),  // 3.1절
        LocalDate.of(year, 5, 5),  // 어린이날
        LocalDate.of(year, 6, 6),  // 현충일
        LocalDate.of(year, 8, 15), // 광복절
        LocalDate.of(year, 10, 3), // 개천절
        LocalDate.of(year, 10, 9), // 한글날
        LocalDate.of(year, 12, 25) // 성탄절
    )
}