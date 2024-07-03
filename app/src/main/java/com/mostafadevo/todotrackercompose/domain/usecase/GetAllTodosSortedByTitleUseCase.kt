package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.data.local.Todo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTodosSortedByTitleUseCase @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase
) {
    operator suspend fun invoke(): kotlinx.coroutines.flow.Flow<List<Todo>> {
        return getAllTodosUseCase().map { todos ->
            todos.sortedBy {
                it.title
            }
        }
    }
}