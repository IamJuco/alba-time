package com.juco.workplacesetting.model

data class UiPayDay(
    val type: UiPayDayType,
    val value: String = "" // "1일", "월요일", "직접설정"
)

enum class UiPayDayType {
    MONTHLY,  // 월급
    WEEKLY,   // 주급
    CUSTOM    // 직접설정
}