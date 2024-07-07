package com.mostafadevo.todotrackercompose.ui.screens.homescreen.todoDetailes

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.LocalTime

data class TodoDetailesBottomSheetUiState(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val priority: Priority = Priority.UNSPECIFIED,
    val date: Long = System.currentTimeMillis(),
    val time: LocalTime = LocalTime.now(),
    val isEditMode: Boolean = false,
    val isCompleted: Boolean = false,
    val isAlarmEnabled: Boolean = false,
)