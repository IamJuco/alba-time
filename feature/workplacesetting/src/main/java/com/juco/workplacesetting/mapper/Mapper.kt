package com.juco.workplacesetting.mapper

import com.juco.domain.model.PayDay
import com.juco.domain.model.PayDayType
import com.juco.workplacesetting.model.UiPayDay
import com.juco.workplacesetting.model.UiPayDayType
import com.juco.workplacesetting.util.convertToPayDays

fun UiPayDay.toDomain(): PayDay {
    return PayDay(
        type = when (this.type) {
            UiPayDayType.MONTHLY -> PayDayType.MONTHLY
            UiPayDayType.WEEKLY -> PayDayType.WEEKLY
            UiPayDayType.CUSTOM -> PayDayType.CUSTOM
        },
        dates = convertToPayDays(this.value)
    )
}

fun UiPayDayType.displayName(): String {
    return when (this) {
        UiPayDayType.MONTHLY -> "월급"
        UiPayDayType.WEEKLY -> "주급"
        UiPayDayType.CUSTOM -> "직접설정"
    }
}