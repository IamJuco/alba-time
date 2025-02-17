package com.juco.domain.repository

import com.juco.domain.model.PayDay
import com.juco.domain.model.WorkPlace
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WorkPlaceRepository {
    suspend fun saveWorkPlace(
        name: String,
        wage: Int,
        workDays: List<LocalDate>,
        payDay: PayDay
    ): Long

    suspend fun getWorkPlaceById(id: Int): WorkPlace?
    suspend fun deleteWorkPlace(workPlace: WorkPlace)
    fun observeWorkPlaces(): Flow<List<WorkPlace>>
}