package com.juco.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.data.util.WorkPlaceConverters

@Database(entities = [WorkPlaceEntity::class], version = 1, exportSchema = false)
@TypeConverters(WorkPlaceConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workPlaceDao(): WorkPlaceDao
}