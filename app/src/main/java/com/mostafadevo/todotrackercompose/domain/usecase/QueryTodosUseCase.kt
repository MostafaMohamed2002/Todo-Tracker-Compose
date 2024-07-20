package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QueryTodosUseCase
@Inject
constructor(
  private val iTodoRepository: ITodoRepository,
) {
  suspend operator fun invoke(query: String): Flow<List<Todo>> =
    iTodoRepository.getTodoItems().map {
      it.filter {
        it.title.contains(query)
      }
    }
}
