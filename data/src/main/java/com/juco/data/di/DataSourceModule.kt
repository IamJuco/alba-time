package com.juco.data.di

import com.juco.data.datasource.WorkPlaceLocalDataSource
import com.juco.data.datasource.WorkPlaceLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindWorkPlaceLocalDataSource(
        dataSourceImpl: WorkPlaceLocalDataSourceImpl
    ): WorkPlaceLocalDataSource
}