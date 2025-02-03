package com.juco.data.datasource

import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.entity.WorkPlaceEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WorkPlaceDataSource {
    suspend fun addWorkPlace(name: String, wage: Int): Long
    suspend fun getWorkPlace(id: Int): WorkPlaceEntity?
    fun getAllWorkPlaces(): Flow<List<WorkPlaceEntity>>
}

class WorkPlaceDataSourceImpl @Inject constructor(
    private val workPlaceDao: WorkPlaceDao
) : WorkPlaceDataSource {
    override suspend fun addWorkPlace(name: String, wage: Int): Long {
        return workPlaceDao.insertWorkPlace(WorkPlaceEntity(name = name, wage = wage))
    }

    override suspend fun getWorkPlace(id: Int): WorkPlaceEntity? {
        return workPlaceDao.getWorkPlaceById(id)
    }

    override fun getAllWorkPlaces(): Flow<List<WorkPlaceEntity>> {
        return workPlaceDao.getAllWorkPlaces()
    }
}