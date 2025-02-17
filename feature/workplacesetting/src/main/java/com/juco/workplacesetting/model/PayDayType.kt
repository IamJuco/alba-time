package com.juco.workplacesetting.model

enum class PayDayType(val displayName: String) {
    MONTHLY("월급"),
    WEEKLY("주급"),
    CUSTOM("직접설정");

    companion object {
        fun fromDisplayName(name: String): PayDayType {
            return entries.find { it.displayName == name } ?: MONTHLY
        }
    }
}

sealed class PayDayValue(val displayName: String) {
    data class Monthly(val day: String) : PayDayValue("$day 마다")
    data class Weekly(val dayOfWeek: String) : PayDayValue("$dayOfWeek 마다")
    data object Custom : PayDayValue("직접 설정")

    companion object {
        fun fromDisplayName(displayName: String, type: PayDayType): PayDayValue {
            return when (type) {
                PayDayType.MONTHLY -> Monthly(displayName)
                PayDayType.WEEKLY -> Weekly(displayName)
                else -> Custom
            }
        }
    }
}