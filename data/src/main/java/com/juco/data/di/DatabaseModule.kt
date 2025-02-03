package com.juco.data.di

import android.content.Context
import androidx.room.Room
import com.juco.data.local.dao.WorkPlaceDao
import com.juco.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "workplace-db"
        ).build()
    }

    @Provides
    fun provideWorkPlaceDao(database: AppDatabase): WorkPlaceDao {
        return database.workPlaceDao()
    }
}
