package com.juco.data.datasource

import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.data.util.toDateString
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

interface WorkPlaceDataSource {
    suspend fun saveWorkPlace(name: String, wage: Int, workDays: List<LocalDate>): Long
    suspend fun getWorkPlaceById(id: Int): WorkPlaceEntity?
    suspend fun deleteWorkPlace(workPlace: WorkPlaceEntity)
    fun getAllWorkPlaces(): Flow<List<WorkPlaceEntity>>
}

class WorkPlaceDataSourceImpl @Inject constructor(
    private val workPlaceDao: WorkPlaceDao
) : WorkPlaceDataSource {
    override suspend fun saveWorkPlace(name: String, wage: Int, workDays: List<LocalDate>): Long {
        return workPlaceDao.insertWorkPlace(
            WorkPlaceEntity(
                name = name,
                wage = wage,
                workDays = workDays.toDateString()
            )
        )
    }

    override suspend fun getWorkPlaceById(id: Int): WorkPlaceEntity? {
        return workPlaceDao.getWorkPlaceById(id)
    }

    override suspend fun deleteWorkPlace(workPlace: WorkPlaceEntity) {
        workPlaceDao.deleteWorkPlace(workPlace)
    }

    override fun getAllWorkPlaces(): Flow<List<WorkPlaceEntity>> {
        return workPlaceDao.getAllWorkPlaces()
    }
}
