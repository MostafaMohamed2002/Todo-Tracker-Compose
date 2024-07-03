package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val iTodoRepository: ITodoRepository
) {
    operator suspend fun invoke(todo: Todo) {
        iTodoRepository.deleteTodo(todo)
    }
}