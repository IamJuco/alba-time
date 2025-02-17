package com.juco.data.mapper

import com.google.gson.Gson
import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.domain.model.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun WorkPlaceEntity.toDomain(): WorkPlace {
    return WorkPlace(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays.toLocalDateList(),
        payDay = payDay.fromJson()
    )
}

fun WorkPlace.toEntity(): WorkPlaceEntity {
    return WorkPlaceEntity(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays.toDateString(),
        payDay = payDay.toJson()
    )
}

// Json 맵핑
fun PayDay.toJson(): String {
    return Gson().toJson(this)
}

fun String.fromJson(): PayDay {
    return runCatching { Gson().fromJson(this, PayDay::class.java) }
        .getOrElse { PayDay(PayDayType.MONTHLY, "1일") }
}

// LocalDate "yyyy-mm-dd" 형식을 String 으로 맵핑
fun List<LocalDate>.toDateString(): String {
    return joinToString(",") { it.format(DateTimeFormatter.ISO_LOCAL_DATE) }
}

fun String.toLocalDateList(): List<LocalDate> {
    return split(",").mapNotNull {
        runCatching { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }.getOrNull()
    }
}