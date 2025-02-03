package com.juco.data.di

import com.juco.data.datasource.WorkPlaceDataSource
import com.juco.data.datasource.WorkPlaceDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindWorkPlaceDataSource(
        dataSourceImpl: WorkPlaceDataSourceImpl
    ): WorkPlaceDataSource
}