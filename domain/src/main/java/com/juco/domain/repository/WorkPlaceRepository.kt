package com.juco.domain.repository

import com.juco.domain.model.PayDay
import com.juco.domain.model.WorkPlace
import com.juco.domain.model.WorkTime
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WorkPlaceRepository {
    suspend fun saveWorkPlace(
        name: String,
        wage: Long,
        workDays: List<LocalDate>,
        payDay: PayDay,
        workTime: WorkTime,
        breakTime: Int,
        workPlaceCardColor: Int,
        isWeeklyHolidayAllowance: Boolean,
        tax: Float
    ): Long

    suspend fun getWorkPlaceById(id: Int): WorkPlace?
    suspend fun deleteWorkPlace(workPlace: WorkPlace)
    suspend fun updateWorkPlace(workPlace: WorkPlace)
    fun observeWorkPlaces(): Flow<List<WorkPlace>>
}