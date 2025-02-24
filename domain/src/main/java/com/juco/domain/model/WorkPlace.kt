package com.juco.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class WorkPlace(
    val id: Int = 0,
    val name: String,
    val wage: Long,
    val workDays: List<LocalDate>,
    val payDay: PayDay,
    val workTime: WorkTime,
    val breakTime: Int,
    val workPlaceCardColor: Int,
    val isWeeklyHolidayAllowance: Boolean
)

data class WorkTime(
    val workStartTime: LocalTime,
    val workEndTime: LocalTime
)