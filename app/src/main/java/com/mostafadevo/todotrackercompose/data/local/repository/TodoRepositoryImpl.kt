package com.mostafadevo.todotrackercompose.data.local.repository

import com.mostafadevo.todotrackercompose.data.local.ITodoDao
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl
@Inject
constructor(
  private val todoDao: ITodoDao,
) : ITodoRepository {
  override suspend fun getTodoItems(): Flow<List<Todo>> {
    return todoDao.getTodoItems()
  }

  override suspend fun getTodoById(id: Int): Todo {
    todoDao.getTodoById(id).let {
      return it
    }
  }

  override suspend fun upsertTodo(todo: Todo) {
    todoDao.upsertTodo(todo)
  }

  override suspend fun deleteTodo(todo: Todo) {
    todoDao.deleteTodo(todo)
  }

  override suspend fun markTodoAsCompleted(todoId: Int) {
    todoDao.markTodoAsCompleted(todoId)
  }
}
