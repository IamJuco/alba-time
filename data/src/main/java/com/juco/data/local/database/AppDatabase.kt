package com.juco.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.entity.WorkPlaceEntity
import com.juco.data.util.WorkPlaceConverters

@Database(
    version = 2,
    entities = [WorkPlaceEntity::class],
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2)]
)
@TypeConverters(WorkPlaceConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workPlaceDao(): WorkPlaceDao
}

// 칼럼 추가시 version 올리고 from, to 하나씩 올리기
// ex
// version = 3
// autoMigrations = [AutoMigration(from = 2, to = 3)]