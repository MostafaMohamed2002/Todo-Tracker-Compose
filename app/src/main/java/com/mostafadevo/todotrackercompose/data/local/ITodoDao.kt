package com.mostafadevo.todotrackercompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITodoDao {
    @Query("SELECT * FROM TODOS")
    fun getTodoItems(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TODOS WHERE id = :id")
    suspend fun getTodoById(id: Int): TodoEntity

    @Upsert
    suspend fun upsertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)
}