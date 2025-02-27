package com.juco.common

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.temporal.TemporalAdjusters

// 주휴수당 계산
// ( 한 주 총 근무일 / 40 ) x 한 주 평균 근무 일 수 x 시급
object WageCalculator {
    data class MonthlyWageResult(
        val totalBaseSalary: Long, // 한달 기본 급여 ( 주휴수당, 세금 제외 )
        val totalTaxAmount: Long, // 세금
        val totalSalary: Long // 한달 총 급여 ( 주휴수당, 세금 포함 )
    )

    // 근무 시간 계산
    private fun calculateWorkTime(
        startTime: LocalTime,
        endTime: LocalTime,
        breakTime: Int,
        workDays: List<LocalDate>,
        yearMonth: YearMonth
    ): Int {
        val firstDayOfMonth = yearMonth.atDay(1)
        val lastDayOfMonth = yearMonth.atEndOfMonth()
        val actualWorkDays = workDays.filter { it in firstDayOfMonth..lastDayOfMonth }

        val startMinutes = startTime.hour * 60 + startTime.minute
        val endMinutes = endTime.hour * 60 + endTime.minute
        val dailyWorkMinutes = (endMinutes - startMinutes) - breakTime
        val totalWorkMinutes = actualWorkDays.size * dailyWorkMinutes
        return totalWorkMinutes / 60
    }

    // 한 달 전체 주휴수당 계산
    fun calculateMonthlyWeeklyAllowance(
        wage: Long,
        startTime: LocalTime,
        endTime: LocalTime,
        breakTime: Int,
        workDays: List<LocalDate>,
        yearMonth: YearMonth
    ): Long {
        var totalWeeklyAllowance = 0L
        val groupedByWeek = workDays
            .filter { it in yearMonth.atDay(1)..yearMonth.atEndOfMonth() }
            .groupBy { it.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) }

        for (weekWorkDays in groupedByWeek.values) {
            val totalWorkHours = calculateWorkTime(startTime, endTime, breakTime, weekWorkDays, yearMonth)
            if (totalWorkHours >= 15) {
                val avgDailyWorkHours = totalWorkHours / weekWorkDays.size.toDouble()
                val weeklyAllowance = (totalWorkHours / 40.0) * avgDailyWorkHours * wage
                totalWeeklyAllowance += weeklyAllowance.toLong()
            }
        }
        return totalWeeklyAllowance
    }

    // 한 달 총 급여 (주휴수당 포함)
    fun calculateMonthlyWageWithAllowance(
        wage: Long,
        startTime: LocalTime,
        endTime: LocalTime,
        breakTime: Int,
        workDays: List<LocalDate>,
        isWeeklyHolidayAllowance: Boolean,
        yearMonth: YearMonth,
        taxRate: Float
    ): MonthlyWageResult {
        val totalWorkHours = calculateWorkTime(startTime, endTime, breakTime, workDays, yearMonth)
        val baseSalary = totalWorkHours * wage
        val weeklyAllowance = if (isWeeklyHolidayAllowance) {
            calculateMonthlyWeeklyAllowance(wage, startTime, endTime, breakTime, workDays, yearMonth)
        } else 0L

        val totalSalary = baseSalary + weeklyAllowance
        val taxAmount = (totalSalary * (taxRate / 100)).toLong()
        val totalSalaryAfterTax = totalSalary - taxAmount

        return MonthlyWageResult(
            totalBaseSalary = baseSalary,
            totalTaxAmount = taxAmount,
            totalSalary = totalSalaryAfterTax
        )
    }

}