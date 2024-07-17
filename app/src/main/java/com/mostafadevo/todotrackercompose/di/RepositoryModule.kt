package com.mostafadevo.todotrackercompose.di

import com.mostafadevo.todotrackercompose.data.local.repository.DataStoreRepositoryImpl
import com.mostafadevo.todotrackercompose.data.local.repository.TodoRepositoryImpl
import com.mostafadevo.todotrackercompose.domain.repository.IDataStoreRepository
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun providesDataStoreRepository(
        dataStoreRepositoryImpl: DataStoreRepositoryImpl
    ): IDataStoreRepository = dataStoreRepositoryImpl

    @Provides
    fun providesTodoRepository(
        todoRepositoryImpl: TodoRepositoryImpl
    ): ITodoRepository = todoRepositoryImpl
}