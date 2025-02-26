package com.juco.domain.model

import java.time.LocalDate

data class PayDay(
    val type: PayDayType,
    val dates: List<LocalDate>
)

enum class PayDayType {
    MONTHLY,
    WEEKLY,
//    CUSTOM
}