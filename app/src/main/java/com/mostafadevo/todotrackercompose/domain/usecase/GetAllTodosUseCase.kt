package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val iTodoRepository: ITodoRepository
) {
    suspend operator fun invoke() = iTodoRepository.getTodoItems()
}