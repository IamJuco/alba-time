package com.juco.feature.calendar.util

import com.github.fj.koreanlunarcalendar.KoreanLunarCalendarUtils
import com.github.fj.koreanlunarcalendar.KoreanLunarDate

// 음력 공휴일 계산
fun lunarHolidays(year: Int): List<KoreanLunarDate> {
    val holidays = mutableListOf<KoreanLunarDate>()

    // 설날 전날, 당일, 다음날
    val lastDayOfLunarDecember = KoreanLunarCalendarUtils.getDaysOfLunarMonth(year - 1, 12, false)

    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year - 1, 12, lastDayOfLunarDecember, false // 작년 12월 말일
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 1, 1, false // 음력 1월 1일 (설날)
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 1, 2, false // 음력 1월 2일 (설 다음날)
        )
    )

    // 부처님 오신 날 (음력 4월 8일)
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 4, 8, false
        )
    )

    // 추석 전날, 당일, 다음날
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 14, false // 음력 8월 14일
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 15, false // 음력 8월 15일 (추석)
        )
    )
    holidays.add(
        KoreanLunarCalendarUtils.getSolarDateOf(
            year, 8, 16, false // 음력 8월 16일
        )
    )

    return holidays
}