package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.Utils.toSortOrder
import com.mostafadevo.todotrackercompose.data.local.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTodosSortedByPriorityUseCase @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase
) {
    suspend operator fun invoke(): Flow<List<Todo>> {
        return getAllTodosUseCase().map { todos ->
            todos.sortedWith(compareByDescending {
                it.priority.toSortOrder()
            })
        }
    }
}

