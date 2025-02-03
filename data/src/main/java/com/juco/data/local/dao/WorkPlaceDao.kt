package com.juco.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juco.data.local.entity.WorkPlaceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkPlaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkPlace(workPlace: WorkPlaceEntity): Long

    @Query("SELECT * FROM work_place WHERE id = :id")
    suspend fun getWorkPlaceById(id: Int): WorkPlaceEntity?

    @Query("SELECT * FROM work_place")
    fun getAllWorkPlaces(): Flow<List<WorkPlaceEntity>>

    @Delete
    suspend fun deleteWorkPlace(workPlace: WorkPlaceEntity)
}