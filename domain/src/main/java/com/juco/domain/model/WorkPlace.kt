package com.juco.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class WorkPlace(
    val id: Int = 0,
    val name: String,
    val wage: Long,
    val workDays: List<LocalDate>,
    val payDay: PayDay = PayDay(PayDayType.MONTHLY, emptyList()),
    val workTime: WorkTime = WorkTime(LocalTime.of(9, 0), LocalTime.of(18, 0)),
    val breakTime: Int,
    val workPlaceCardColor: Int,
    val isWeeklyHolidayAllowance: Boolean,
    val tax: Float
)


data class WorkTime(
    val workStartTime: LocalTime,
    val workEndTime: LocalTime
)