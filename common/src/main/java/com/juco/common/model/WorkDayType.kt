package com.juco.common.model

import java.time.DayOfWeek

enum class WorkDayType(val displayName: String, val dayOfWeeks: Set<DayOfWeek>) {
    WEEKDAYS("월~금 (주 5일 근무)",
        setOf(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)),
    WEEKENDS("주말 (토, 일 근무)",
        setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)),
    CUSTOM("직접 설정", emptySet())
}