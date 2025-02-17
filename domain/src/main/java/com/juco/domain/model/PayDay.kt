package com.juco.domain.model

data class PayDay(
    val type: PayDayType,
    val value: String = "" // "1일", "월요일", "직접설정"
)

enum class PayDayType {
    MONTHLY,  // 월급
    WEEKLY,   // 주급
    CUSTOM    // 직접설정
}