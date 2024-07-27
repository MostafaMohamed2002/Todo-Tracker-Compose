package com.mostafadevo.todotrackercompose.ui.screens.homescreen.edittodoscreen

import com.mostafadevo.todotrackercompose.data.local.Priority
import java.time.LocalTime

data class EditScreenUiState(
  val id: Int = 0,
  val title: String = "",
  val description: String? = "",
  val priority: Priority = Priority.UNSPECIFIED,
  val date: Long = System.currentTimeMillis(),
  val time: LocalTime = LocalTime.now(),
  val isAlarmEnabled: Boolean = false,
)
