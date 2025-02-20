package com.juco.data.mapper

import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.data.util.gson
import com.juco.domain.model.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun WorkPlaceEntity.toDomain(): WorkPlace {
    return WorkPlace(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays.toLocalDateList(),
        payDay = payDay.toPayDay(),
        workTime = workTime.toWorkTime(),
        workPlaceCardColor = workPlaceCardColor
    )
}

fun WorkPlace.toEntity(): WorkPlaceEntity {
    return WorkPlaceEntity(
        id = id,
        name = name,
        wage = wage,
        workDays = workDays.toDateString(),
        payDay = payDay.toJson(),
        workTime = workTime.toJson(),
        workPlaceCardColor = workPlaceCardColor
    )
}

// Json 맵핑
fun PayDay.toJson(): String {
    return gson.toJson(this)
}

fun String.toPayDay(): PayDay {
    return runCatching { gson.fromJson(this, PayDay::class.java) }
        .getOrElse { PayDay(PayDayType.MONTHLY, emptyList()) }
}

fun WorkTime.toJson(): String {
    return gson.toJson(this)
}

fun String.toWorkTime(): WorkTime {
    return runCatching { gson.fromJson(this, WorkTime::class.java) }
        .getOrElse { WorkTime(LocalTime.of(9, 0), LocalTime.of(18, 0)) }
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