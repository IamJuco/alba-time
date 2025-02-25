package com.juco.data.util

import androidx.room.TypeConverter
import com.juco.domain.model.PayDay
import com.juco.domain.model.PayDayType
import com.juco.domain.model.WorkTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class WorkPlaceConverters {

    private val localDateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val localTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @TypeConverter
    fun fromWorkTime(workTime: WorkTime?): String? {
        return workTime?.let {
            "${it.workStartTime.format(localTimeFormatter)}-${it.workEndTime.format(localTimeFormatter)}"
        }
    }

    @TypeConverter
    fun toWorkTime(workTimeString: String?): WorkTime? {
        return workTimeString?.split("-")?.let {
            if (it.size == 2) {
                WorkTime(
                    LocalTime.parse(it[0], localTimeFormatter),
                    LocalTime.parse(it[1], localTimeFormatter)
                )
            } else null
        }
    }

    @TypeConverter
    fun fromLocalDateList(dates: List<LocalDate>?): String? {
        return dates?.joinToString(",") { it.format(localDateFormatter) }
    }

    @TypeConverter
    fun toLocalDateList(dateString: String?): List<LocalDate>? {
        return dateString?.split(",")?.mapNotNull {
            runCatching { LocalDate.parse(it, localDateFormatter) }.getOrNull()
        }
    }

    @TypeConverter
    fun fromPayDay(payDay: PayDay?): String? {
        return payDay?.let {
            "${it.type.name}:${it.dates.joinToString(",") { date -> date.format(localDateFormatter) }}"
        }
    }

    @TypeConverter
    fun toPayDay(payDayString: String?): PayDay {
        return if (payDayString.isNullOrEmpty()) {
            PayDay(PayDayType.MONTHLY, emptyList())
        } else {
            payDayString.split(":").let {
                val type = runCatching { PayDayType.valueOf(it[0]) }.getOrElse { PayDayType.MONTHLY }
                val dates = it.getOrNull(1)?.split(",")?.mapNotNull {
                    runCatching { LocalDate.parse(it, localDateFormatter) }.getOrNull()
                } ?: emptyList()
                PayDay(type, dates)
            }
        }
    }
}
