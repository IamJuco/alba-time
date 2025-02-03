package com.juco.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.entity.WorkPlaceEntity

@Database(entities = [WorkPlaceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workPlaceDao(): WorkPlaceDao
}