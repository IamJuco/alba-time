package com.juco.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

/**
 * 📌 한 달 기준 총 월급 계산기 (주휴수당 포함 가능)
 */
fun monthlyWageTotalCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>,
    isWeeklyHolidayAllowance: Boolean,
    yearMonth: YearMonth
): Long {
    val firstDayOfMonth = yearMonth.atDay(1)
    val lastDayOfMonth = yearMonth.atEndOfMonth()

    // ✅ 실제 근무한 날짜만 필터링
    val actualWorkDays = workDays.filter { it in firstDayOfMonth..lastDayOfMonth }
    val totalWorkDays = actualWorkDays.size

    // ✅ 하루 근무시간 (휴게시간 제외)
    val startMinutes = startTime.hour * 60 + startTime.minute
    val endMinutes = endTime.hour * 60 + endTime.minute
    val dailyWorkMinutes = (endMinutes - startMinutes) - breakTime
    val totalWorkMinutes = totalWorkDays * dailyWorkMinutes
    val totalWorkHours = totalWorkMinutes / 60

    // ✅ 기본 급여 계산
    var totalSalary = totalWorkHours * wage

    // ✅ 주휴수당 포함 여부 확인
    if (isWeeklyHolidayAllowance) {
        val weeklyAllowance = weeklyAllowanceTotalCalculator(
            wage = wage,
            startTime = startTime,
            endTime = endTime,
            breakTime = breakTime,
            workDays = actualWorkDays
        )
        totalSalary += weeklyAllowance
    }

    return totalSalary
}

/**
 * 📌 한 주 기준 주휴수당 계산기
 */
fun weeklyAllowanceTotalCalculator(
    wage: Long,
    startTime: LocalTime,
    endTime: LocalTime,
    breakTime: Int,
    workDays: List<LocalDate>
): Long {
    var totalWeeklyAllowance = 0L

    // ✅ 월별 주 단위로 근무일을 그룹화하여 계산 (월요일~일요일)
    val groupedByWeek =
        workDays.groupBy { it.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) }

    for (weekWorkDays in groupedByWeek.values) {
        // ✅ 이번 주 총 근무 시간 계산 (휴게시간 제외)
        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute
        val totalWorkMinutes = weekWorkDays.size * ((endMinutes - startMinutes) - breakTime)
        val totalWorkHours = totalWorkMinutes / 60

        // ✅ 1주 동안 15시간 이상 근무하면 주휴수당 지급
        if (totalWorkHours >= 15) {
            val avgDailyWorkHours = totalWorkHours / weekWorkDays.size.toDouble() // 평균 하루 근무 시간
            val weeklyAllowance = (totalWorkHours / 40.0) * avgDailyWorkHours * wage
            totalWeeklyAllowance += weeklyAllowance.toLong()
        }
    }
    return totalWeeklyAllowance
}