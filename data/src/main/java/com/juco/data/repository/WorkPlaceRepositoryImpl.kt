package com.juco.data.repository

import com.juco.data.datasource.WorkPlaceDataSource
import com.juco.data.mapper.toDomain
import com.juco.domain.model.WorkPlace
import com.juco.domain.repository.WorkPlaceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WorkPlaceRepositoryImpl @Inject constructor(
    private val localDataSource: WorkPlaceDataSource
) : WorkPlaceRepository {
    override suspend fun saveWorkPlace(name: String, wage: Int): Long {
        return localDataSource.addWorkPlace(name, wage)
    }

    override suspend fun getWorkPlaceById(id: Int): WorkPlace? {
        return localDataSource.getWorkPlace(id)?.toDomain()
    }

    override fun observeWorkPlaces(): Flow<List<WorkPlace>> {
        return localDataSource.getAllWorkPlaces().map { list ->
            list.map { it.toDomain() }
        }
    }
}