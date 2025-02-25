package com.juco.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

// 한 달 기준 총 월급 계산기 (주휴수당 미포함)
fun monthlyWageTotalCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>,
    yearMonth: YearMonth,
): Long {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    val actualWorkDays = workDays.filter { it in firstDayOfMonth..lastDayOfMonth }
    val totalWorkDays = actualWorkDays.size

    val startMinutes = startTime.hour * 60 + startTime.minute
    val endMinutes = endTime.hour * 60 + endTime.minute
    val dailyWorkMinutes = (endMinutes - startMinutes) - breakTime
    val totalWorkMinutes = totalWorkDays * dailyWorkMinutes
    val totalWorkHours = totalWorkMinutes / 60
    val totalSalary = totalWorkHours * wage

    return totalSalary
}

// 한 달 기준 총 월급 계산기 (주휴수당 포함)
fun monthlyWageTotalWithAllowanceCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>,
    isWeeklyHolidayAllowance: Boolean,
    yearMonth: YearMonth,
    taxRate: Float
): Long {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    val actualWorkDays = workDays.filter { it in firstDayOfMonth..lastDayOfMonth }
    val totalWorkDays = actualWorkDays.size

    val startMinutes = startTime.hour * 60 + startTime.minute
    val endMinutes = endTime.hour * 60 + endTime.minute
    val dailyWorkMinutes = (endMinutes - startMinutes) - breakTime
    val totalWorkMinutes = totalWorkDays * dailyWorkMinutes
    val totalWorkHours = totalWorkMinutes / 60
    var totalSalary = totalWorkHours * wage

    if (isWeeklyHolidayAllowance) {
        val weeklyAllowance = monthlyTotalWeeklyAllowanceTotalCalculator(
            wage = wage,
            startTime = startTime,
            endTime = endTime,
            breakTime = breakTime,
            workDays = actualWorkDays
        )
        totalSalary += weeklyAllowance
    }
    val taxAmount = (totalSalary * (taxRate / 100)).toLong()

    return totalSalary - taxAmount
}

// 한달 총 월급및 주휴 수당 계산
fun monthlyTotalWeeklyAllowanceTotalCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>
): Long {
    var totalWeeklyAllowance = 0L
    val groupedByWeek =
        workDays.groupBy { it.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) }

    for (weekWorkDays in groupedByWeek.values) {
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute
        val totalWorkMinutes = weekWorkDays.size * ((endMinutes - startMinutes) - breakTime)
        val totalWorkHours = totalWorkMinutes / 60

        if (totalWorkHours >= 15) {
            val avgDailyWorkHours = totalWorkHours / weekWorkDays.size.toDouble()
            val weeklyAllowance = (totalWorkHours / 40.0) * avgDailyWorkHours * wage
            totalWeeklyAllowance += weeklyAllowance.toLong()
        }
    }
    return totalWeeklyAllowance
}

// 한 주 주휴수당 계산 ( 일요일 기준 )
// ( 한 주 총 근무일 / 40 ) x 한 주 평균 근무 일 수 x 시급
fun weeklyAllowanceTotalCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>,
    currentDate: LocalDate = LocalDate.now()
): Long {
    val currentWeekStart = currentDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    val currentWeekEnd = currentWeekStart.with(DayOfWeek.SUNDAY)

    val currentWeekWorkDays = workDays.filter { it in currentWeekStart..currentWeekEnd }

    val startMinutes = startTime.hour * 60 + startTime.minute
    val endMinutes = endTime.hour * 60 + endTime.minute
    val totalWorkMinutes = currentWeekWorkDays.size * ((endMinutes - startMinutes) - breakTime)
    val totalWorkHours = totalWorkMinutes / 60

    if (totalWorkHours >= 15) {
        val avgDailyWorkHours = totalWorkHours / currentWeekWorkDays.size.toDouble()
        return ((totalWorkHours / 40.0) * avgDailyWorkHours * wage).toLong()
    }
    return 0L
}