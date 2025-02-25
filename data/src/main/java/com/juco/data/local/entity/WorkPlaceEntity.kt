package com.juco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juco.domain.model.PayDay
import com.juco.domain.model.WorkTime
import java.time.LocalDate

@Entity(tableName = "work_place")
data class WorkPlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // 근무지 이름
    val wage: Long, // 시급
    val workDays: List<LocalDate>, // 근무일 리스트
    val payDay: PayDay?, // 급여일
    val workTime: WorkTime?, // 근무 시간
    val breakTime: Int, // 휴게 시간
    val workPlaceCardColor: Int, // 근무 카드 색상
    val isWeeklyHolidayAllowance: Boolean // 주휴 수당 여부
)