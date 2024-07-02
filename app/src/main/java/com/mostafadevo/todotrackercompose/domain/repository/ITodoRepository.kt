package com.mostafadevo.todotrackercompose.domain.repository

import com.mostafadevo.todotrackercompose.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    suspend fun getTodoItems(): Flow<List<TodoEntity>>
    suspend fun getTodoById(id: Int): TodoEntity
    suspend fun upsertTodo(todo: TodoEntity)
    suspend fun deleteTodo(todo: TodoEntity)
}