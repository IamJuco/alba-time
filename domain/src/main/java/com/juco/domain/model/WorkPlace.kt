package com.juco.domain.model

import java.time.LocalDate

data class WorkPlace(
    val id: Int = 0,
    val name: String,
    val wage: Long,
    val workDays: List<LocalDate>,
    val payDay: PayDay
)