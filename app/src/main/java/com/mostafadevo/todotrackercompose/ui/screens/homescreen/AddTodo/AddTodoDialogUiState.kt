package com.mostafadevo.todotrackercompose.ui.screens.homescreen.AddTodo

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.LocalTime

data class AddTodoDialogUiState(
  val title: String = "",
  val description: String = "",
  val priority: Priority = Priority.UNSPECIFIED,
  val date: Long = System.currentTimeMillis(),
  val time: LocalTime = LocalTime.now(),
  var isAlarmEnabled: Boolean = false,
)
