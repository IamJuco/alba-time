package com.juco.domain.repository

import com.juco.domain.model.WorkPlace
import kotlinx.coroutines.flow.Flow

interface WorkPlaceRepository {
    suspend fun saveWorkPlace(name: String, wage: Int): Long
    suspend fun getWorkPlaceById(id: Int): WorkPlace?
    suspend fun deleteWorkPlace(workPlace: WorkPlace)
    fun observeWorkPlaces(): Flow<List<WorkPlace>>
}