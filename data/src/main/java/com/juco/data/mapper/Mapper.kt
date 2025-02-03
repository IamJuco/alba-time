package com.juco.data.mapper

import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.domain.model.WorkPlace

fun WorkPlaceEntity.toDomain() = WorkPlace(
    id = this.id,
    name = this.name,
    wage = this.wage
)

fun WorkPlace.toEntity() = WorkPlaceEntity(
    id = this.id,
    name = this.name,
    wage = this.wage
)