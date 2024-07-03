package com.mostafadevo.todotrackercompose.data.local.repository

import com.mostafadevo.todotrackercompose.data.local.ITodoDao
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: ITodoDao
) : ITodoRepository {
    override suspend fun getTodoItems(): Flow<List<Todo>> {
        return flow {
            todoDao.getTodoItems().collect {
                it.let {
                    emit(it)
                }
            }
        }
    }

    override suspend fun getTodoById(id: Int): Todo {
        todoDao.getTodoById(id).let {
            return it
        }
    }

    override suspend fun upsertTodo(todo: Todo) {
        todo.let {
            todoDao.upsertTodo(todo)
        }
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo)
    }
}