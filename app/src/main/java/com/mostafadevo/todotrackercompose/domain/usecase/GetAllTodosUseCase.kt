package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import com.mostafadevo.todotrackercompose.ui.screens.homescreen.SortingOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val iTodoRepository: ITodoRepository
) {
    suspend operator fun invoke(
        showCompleted: Int,
        sortingOptions: SortingOptions
    ): Flow<List<Todo>> {
        return iTodoRepository.getTodoItems().map { todos ->
            val filteredTodos = if (showCompleted == 1) {
                todos.filter {
                    it.isCompleted
                }
            } else {
                todos.filter {
                    !it.isCompleted
                }
            }

            when (sortingOptions) {
                SortingOptions.BY_TITLE -> {
                    filteredTodos.sortedBy { todo ->
                        todo.title
                    }
                }

                SortingOptions.BY_PRIORITY -> {
                    filteredTodos.sortedBy {
                        it.priority
                    }
                }
            }
        }
    }
}
