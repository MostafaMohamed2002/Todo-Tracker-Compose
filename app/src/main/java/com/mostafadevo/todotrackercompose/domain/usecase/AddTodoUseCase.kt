package com.mostafadevo.todotrackercompose.domain.usecase

import com.mostafadevo.todotrackercompose.Utils.TodoReminderManager
import com.mostafadevo.todotrackercompose.data.local.Todo
import com.mostafadevo.todotrackercompose.domain.repository.ITodoRepository
import javax.inject.Inject

class AddTodoUseCase
@Inject
constructor(
  private val iTodoRepository: ITodoRepository,
  private val todoReminderManager: TodoReminderManager,
) {
  suspend operator fun invoke(todo: Todo) {
    iTodoRepository.upsertTodo(todo)
    if (todo.isAlarmEnabled) {
      todoReminderManager.setTodoReminder(todo)
    }
  }
}
