package com.mostafadevo.todotrackercompose.di

import android.content.Context
import androidx.room.Room
import com.mostafadevo.todotrackercompose.data.local.ITodoDao
import com.mostafadevo.todotrackercompose.data.local.TodoDatabase
import com.mostafadevo.todotrackercompose.data.local.repository.TodoRepositoryImpl
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun providesTodoDatabase(
        @ApplicationContext context: Context
    ): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").build()
    }

    @Provides
    @Singleton
    fun providesTodoDao(
        todoDatabase: TodoDatabase
    ): ITodoDao = todoDatabase.todoDao()

    @Provides
    fun providesTodoRepository(
        todoRepositoryImpl: TodoRepositoryImpl
    ): ITodoRepository = todoRepositoryImpl
}