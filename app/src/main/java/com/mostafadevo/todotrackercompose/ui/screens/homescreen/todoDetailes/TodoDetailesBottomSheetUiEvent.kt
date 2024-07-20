package com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.LocalTime

sealed class TodoDetailesBottomSheetUiEvent {
  object ToggleEditMode : TodoDetailesBottomSheetUiEvent()

  data class onTitleChange(
    val title: String,
  ) : TodoDetailesBottomSheetUiEvent()

  data class onDescriptionChange(
    val description: String,
  ) : TodoDetailesBottomSheetUiEvent()

  data class onPriorityChange(
    val priority: Priority,
  ) : TodoDetailesBottomSheetUiEvent()

  data class onDateChange(
    val date: Long,
  ) : TodoDetailesBottomSheetUiEvent()

  data class onTimeChange(
    val time: LocalTime,
  ) : TodoDetailesBottomSheetUiEvent()

  object onDeleted : TodoDetailesBottomSheetUiEvent()
}
