package com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.LocalTime

sealed class AddTodoDialogUiEvents {
  object AddTodo : AddTodoDialogUiEvents()

  object Close : AddTodoDialogUiEvents()

  data class OnTitleChange(
    val title: String,
  ) : AddTodoDialogUiEvents()

  data class OnDescriptionChange(
    val description: String,
  ) : AddTodoDialogUiEvents()

  data class OnPriorityChange(
    val priority: Priority,
  ) : AddTodoDialogUiEvents()

  data class OnDateChange(
    val date: Long,
  ) : AddTodoDialogUiEvents()

  data class OnTimeChange(
    val time: LocalTime,
  ) : AddTodoDialogUiEvents()

  data class OnSetAlarmChange(
    val isEnabled: Boolean,
  ) : AddTodoDialogUiEvents()
}
