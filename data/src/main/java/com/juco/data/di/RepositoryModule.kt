package com.juco.data.di

import com.juco.data.repository.WorkPlaceRepositoryImpl
import com.juco.domain.repository.WorkPlaceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWorkPlaceRepository(
        workPlaceRepositoryImpl: WorkPlaceRepositoryImpl
    ): WorkPlaceRepository
}