package com.mostafadevo.todotrackercompose.domain.repository

import com.mostafadevo.todotrackercompose.data.local.Todo
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    suspend fun getTodoItems(): Flow<List<Todo>>
    suspend fun getTodoById(id: Int): Todo
    suspend fun upsertTodo(todo: Todo)
    suspend fun deleteTodo(todo: Todo)
}