package com.juco.workplacesetting.util

import com.juco.domain.model.PayDayType

fun PayDayType.displayName(): String {
    return when (this) {
        PayDayType.MONTHLY -> "월급"
        PayDayType.WEEKLY -> "주급"
        PayDayType.CUSTOM -> "직접설정"
    }
}