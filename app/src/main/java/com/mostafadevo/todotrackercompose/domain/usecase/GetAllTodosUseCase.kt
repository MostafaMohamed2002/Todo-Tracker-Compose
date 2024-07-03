package com.mostafadevo.todotrackercompose.domain.usecase;

import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val iTodoRepository: ITodoRepository
) {
    suspend operator fun invoke(): Flow<List<Todo>> {
        return iTodoRepository.getTodoItems()
    }
}
