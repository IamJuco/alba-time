package com.juco.data.mapper

import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.domain.model.WorkPlace
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun WorkPlaceEntity.toDomain() = WorkPlace(
    id = this.id,
    name = this.name,
    wage = this.wage,
    workDays = this.workDays.toLocalDateList()
)

fun WorkPlace.toEntity() = WorkPlaceEntity(
    id = this.id,
    name = this.name,
    wage = this.wage,
    workDays = this.workDays.toDateString()
)

// "yyyy-MM-dd" 포맷
private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

fun String.toLocalDateList(): List<LocalDate> {
    return if (this.isEmpty()) emptyList()
    else this.split(",").mapNotNull { dateString ->
        runCatching { LocalDate.parse(dateString, formatter) }.getOrNull()
    }
}

fun List<LocalDate>.toDateString(): String {
    return this.joinToString(",") { it.format(formatter) }
}