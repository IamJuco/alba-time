package com.juco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_place")
data class WorkPlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // 근무지 id
    val name: String, // 근무지 이름
    val wage: Long, // 시급
    val workDays: String, // 근무 하는 기간 ( Json )
    val payDay: String, // 월급 받는 날 ( Json )
    val workTime: String, // 근무 하는 시간 ( Json )
    val breakTime: Int, // 휴게 시간
    val workPlaceCardColor: Int, // 근무 카드 색상
    val isWeeklyHolidayAllowance: Boolean // 주휴 수당 여부
)