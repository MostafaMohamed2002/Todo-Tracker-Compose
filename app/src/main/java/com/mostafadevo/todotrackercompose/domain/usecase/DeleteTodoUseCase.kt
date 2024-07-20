package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import javax.inject.Inject

class DeleteTodoUseCase
@Inject
constructor(
  private val iTodoRepository: ITodoRepository,
) {
  suspend operator fun invoke(todo: Todo) {
    iTodoRepository.deleteTodo(todo)
  }
  // TODO: handle disabling alarm if todo has alarm enabled
}
