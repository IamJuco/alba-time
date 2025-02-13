package com.juco.data.mapper

import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.data.util.toDateString
import com.juco.data.util.toLocalDateList
import com.juco.domain.model.WorkPlace

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