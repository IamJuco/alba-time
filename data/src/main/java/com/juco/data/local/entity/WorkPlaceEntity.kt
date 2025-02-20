package com.juco.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work_place")
data class WorkPlaceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val wage: Long,
    val workDays: String,
    val payDay: String,
    val workTime: String,
    val workPlaceCardColor: Int
)