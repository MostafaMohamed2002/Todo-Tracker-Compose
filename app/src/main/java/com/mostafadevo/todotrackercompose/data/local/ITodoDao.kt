package com.mostafadevo.todotrackercompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ITodoDao {
  @Query("SELECT * FROM TODOS")
  fun getTodoItems(): Flow<List<Todo>>

  @Query("SELECT * FROM TODOS WHERE id = :id")
  suspend fun getTodoById(id: Int): Todo

  @Upsert
  suspend fun upsertTodo(todo: Todo)

  @Delete
  suspend fun deleteTodo(todo: Todo)

  @Query("UPDATE TODOS SET isCompleted = 1 WHERE id = :todoId")
  suspend fun markTodoAsCompleted(todoId: Int)
}
