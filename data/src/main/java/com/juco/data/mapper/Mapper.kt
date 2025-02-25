package com.juco.data.mapper

import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.domain.model.*
import java.time.LocalTime

fun WorkPlaceEntity.toDomain(): WorkPlace {
    return WorkPlace(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays,
        payDay = payDay ?: PayDay(PayDayType.MONTHLY, emptyList()),
        workTime = workTime ?: WorkTime(LocalTime.of(9, 0), LocalTime.of(18, 0)),
        breakTime = breakTime,
        workPlaceCardColor = workPlaceCardColor,
        isWeeklyHolidayAllowance = isWeeklyHolidayAllowance
    )
}

fun WorkPlace.toEntity(): WorkPlaceEntity {
    return WorkPlaceEntity(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays,
        payDay = payDay,
        workTime = workTime,
        breakTime = breakTime,
        workPlaceCardColor = workPlaceCardColor,
        isWeeklyHolidayAllowance = isWeeklyHolidayAllowance
    )
}