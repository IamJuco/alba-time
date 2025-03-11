package com.juco.workplacesetting.mapper

import com.juco.domain.model.PayDay
import com.juco.domain.model.PayDayType
import com.juco.domain.model.WorkTime
import com.juco.common.model.UiPayDay
import com.juco.common.model.UiPayDayType
import com.juco.common.model.UiWorkTime
import com.juco.common.util.convertToPayDays

fun UiPayDay.toDomain(): PayDay {
    return PayDay(
        type = when (this.type) {
            UiPayDayType.MONTHLY -> PayDayType.MONTHLY
            UiPayDayType.WEEKLY -> PayDayType.WEEKLY
//            UiPayDayType.CUSTOM -> PayDayType.CUSTOM
        },
        dates = convertToPayDays(this.value)
    )
}

fun UiWorkTime.toDomain(): WorkTime {
    return WorkTime(
        workStartTime = this.startTime,
        workEndTime = this.endTime
    )
}